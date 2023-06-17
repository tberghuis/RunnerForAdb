package dev.tberghuis.adbrunner.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Entity
data class AdbCommand(
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val title: String,
  val host: String,
  val adbCommandString: String,
)

@Dao
interface AdbCommandDao {
  @Query("SELECT * FROM adbcommand")
  fun getAll(): Flow<List<AdbCommand>>

  @Query("SELECT * FROM adbcommand where id = :id")
  suspend fun loadAdbCommandById(id: Int): AdbCommand

  @Update
  fun update(command: AdbCommand)

  @Insert
  fun insertAll(vararg commands: AdbCommand)

  @Delete
  fun delete(command: AdbCommand)

  @Query("delete FROM adbcommand where id = :id")
  fun deleteById(id: Int)
}
