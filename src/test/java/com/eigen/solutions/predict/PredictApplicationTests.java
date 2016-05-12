package com.eigen.solutions.predict;

import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import com.eigen.solutions.data.Alerts;
import com.eigen.solutions.data.Metric;
import com.eigen.solutions.mongo.connection.MorphiaConnection;

public class PredictApplicationTests {

	@Before
	public void setUp() {
		System.setProperty("DB_NAME", "testDB");
	}

	@Test
	public void testMetricInsert() {

		MetricController metricController = new MetricController();

		Metric metric1 = new Metric();
		metric1.setTimeStamp(145566777L);
		metric1.setValue(67L);
		metricController.create(metric1);

		List<Metric> list = metricController.read();

		Assert.assertEquals(1, list.size());

		Metric metric2 = new Metric();
		metric2.setTimeStamp(145566778L);
		metric2.setValue(68L);
		metricController.create(metric2);

		list = metricController.read();

		Assert.assertEquals(2, list.size());

		Assert.assertEquals(67, list.get(0).getValue().longValue());
		Assert.assertEquals(68, list.get(1).getValue().longValue());

		List<Alerts> alerts = (new AlertController()).read();
		Assert.assertEquals(0, alerts.size());
	}

	@Test
	public void testAlertOverWeightInsert() {

		MetricController metricController = new MetricController();

		Metric metric1 = new Metric();
		metric1.setTimeStamp(145566777L);
		metric1.setValue(67L);
		metricController.create(metric1);

		Metric metric = new Metric();
		metric.setTimeStamp(145566780L);
		metric.setValue(75L);
		metricController.create(metric);

		List<Metric> list = metricController.read();

		Assert.assertEquals(2, list.size());

		List<Alerts> alerts = (new AlertController()).read();
		Assert.assertEquals(1, alerts.size());

		Assert.assertEquals(list.get(1).getTimeStamp().longValue(), alerts.get(0).getTimeStamp().longValue());
		Assert.assertTrue(alerts.get(0).getOverWeight());
		Assert.assertFalse(alerts.get(0).getUnderWeight());
	}

	@Test
	public void testAlertUnderWeightInsert() {

		MetricController metricController = new MetricController();

		Metric metric1 = new Metric();
		metric1.setTimeStamp(145566777L);
		metric1.setValue(67L);
		metricController.create(metric1);

		Metric metric = new Metric();
		metric.setTimeStamp(145566782L);
		metric.setValue(55L);
		metricController.create(metric);

		List<Metric> list = metricController.read();

		Assert.assertEquals(2, list.size());

		List<Alerts> alerts = (new AlertController()).read();
		Assert.assertEquals(1, alerts.size());

		Assert.assertEquals(list.get(1).getTimeStamp().longValue(), alerts.get(0).getTimeStamp().longValue());
		Assert.assertFalse(alerts.get(0).getOverWeight());
		Assert.assertTrue(alerts.get(0).getUnderWeight());
	}

	@After
	public void cleanUp() {
		Datastore ds = MorphiaConnection.getConnection();
		ds.delete(ds.createQuery(Metric.class));
		ds.delete(ds.createQuery(Alerts.class));
	}

}
