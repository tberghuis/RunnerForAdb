package dev.tberghuis.adbrunner

import android.app.Application
import androidx.room.Room
import dev.tberghuis.adbrunner.data.MyDatabase

class MyApplication : Application() {
  val database by lazy {
    Room.databaseBuilder(this, MyDatabase::class.java, "data.db")
      .fallbackToDestructiveMigration()
      .build()
  }
}
