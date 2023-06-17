package dev.tberghuis.adbrunner.data

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
  entities = [
    AdbCommand::class,
  ],
  version = 1,
  exportSchema = false
)
abstract class MyDatabase : RoomDatabase() {
  abstract fun adbCommandDao(): AdbCommandDao
}