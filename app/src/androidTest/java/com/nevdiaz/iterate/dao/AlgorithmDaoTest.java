package com.nevdiaz.iterate.dao;

import android.content.Context;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.filters.SmallTest;
import com.nevdiaz.iterate.IterateDatabase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

@SmallTest
public class AlgorithmDaoTest {

  private IterateDatabase db;
  private AlgorithmDao dao;

  @Before
  public void setup() throws Exception {
    Context context = ApplicationProvider.getApplicationContext();
    db = Room.inMemoryDatabaseBuilder(context, IterateDatabase.class).build();
    dao = db.getAlgorithmDao();
  }

//  @Test
//  public void insert() {
//  Algorithm algorithm = new Algorithm();
//  algorithm.setFormula(Formula.);
//  long id = dao.insert(algorithm);
//  assertTrue(id > 0);
//  }
//
//
//  public void insertNullAlgorithm(){
//    Algorithm algorithm = new Algorithm();
//    long id = dao.insert(algorithm);
//    fail("This shouldn't get here!");
//
//  }


  @Test
  public void findById() {
  }

  @After
  public void tearDown() throws Exception {
    db.close();
  }
}