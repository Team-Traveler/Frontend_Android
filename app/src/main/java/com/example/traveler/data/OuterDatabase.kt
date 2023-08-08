package com.example.traveler.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.traveler.model.OuterDto

/* entities = 사용할 엔티티 선언, version = 엔티티 구조 변경 시 구분해주는 역할
   exportSchema = 스키마 내보내기 설정 */
@Database(entities = [OuterDto::class], version = 1, exportSchema = false)
abstract class OuterDatabase : RoomDatabase(){

    abstract fun outerDao() : OuterDao

    companion object{
        /* @Volatile = 접근가능한 변수의 값을 cache를 통해 사용하지 않고
        thread가 직접 main memory에 접근 하게하여 동기화. */
        @Volatile
        private var instance : OuterDatabase? = null

        // 싱글톤으로 생성 (자주 생성 시 성능 손해). 이미 존재할 경우 생성하지 않고 바로 반환
        fun getDatabase(context : Context) : OuterDatabase? {
            if(instance == null){
                synchronized(OuterDatabase::class){
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        OuterDatabase::class.java,
                        "outer_database"
                    ).build()
                }
            }
            return instance
        }
    }
}