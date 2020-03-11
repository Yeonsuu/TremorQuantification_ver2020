package com.ahnbcilab.tremorquantification.controller

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns
import com.ahnbcilab.tremorquantification.data.PatientData

object DBController {
    object PatientDataDB : BaseColumns {
        const val TABLE_NAME = "Patient"
        const val COLUMN_CLINIC_ID = "clinicID"
        const val COLUMN_PATIENT_NAME = "name"
        const val COLUMN_USER_ID = "uid"
        const val COLUMN_TASK_NO = "taskno"
        const val COLUMN_DISEASE_TYPE = "DiseaseType"
    }


    private const val SQL_CREATE_PATIENT_DATA =
        "CREATE TABLE ${PatientDataDB.TABLE_NAME} (" +
                "${PatientDataDB.COLUMN_CLINIC_ID} VARCHAR(10) PRIMARY KEY," +
                "${PatientDataDB.COLUMN_PATIENT_NAME} VARCHAR(10)," +
                "${PatientDataDB.COLUMN_USER_ID} VARCHAR(20)," +
                "${PatientDataDB.COLUMN_TASK_NO} INTEGER," +
                "${PatientDataDB.COLUMN_DISEASE_TYPE} VARCHAR(10)" + ")"



    private const val SQL_DELETE_PATIENT_DATA =
            "DROP TABLE IF EXISTS ${PatientDataDB.TABLE_NAME}"



    class PatientDataDbHelper(val context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
        companion object {
            const val DATABASE_VERSION = 2
            const val DATABASE_NAME = "PatientData.db"
        }

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL(SQL_CREATE_PATIENT_DATA)
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL(SQL_DELETE_PATIENT_DATA)
            onCreate(db)
        }

        override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            onUpgrade(db, oldVersion, newVersion)
        }

        fun insert(data: PatientData): Boolean {
            val flag: Boolean
            val db = this.writableDatabase

            val values = ContentValues().apply {
                put(PatientDataDB.COLUMN_CLINIC_ID, data.toMap().getValue("ClinicID").toString())
                put(PatientDataDB.COLUMN_PATIENT_NAME, data.toMap().getValue("ClinicName").toString())
                put(PatientDataDB.COLUMN_USER_ID, data.toMap().getValue("UserID").toString())
                put(PatientDataDB.COLUMN_TASK_NO, data.toMap().getValue("TaskNo") as Int)
                put(PatientDataDB.COLUMN_DISEASE_TYPE, data.toMap().getValue("DiseaseType").toString())
            }

            val newRowId = db.insert(PatientDataDB.TABLE_NAME, null, values)
            if (newRowId >= 0) {
                //data.id = newRowId.toInt()
                flag = true
            }
            else
                flag = false

            return flag
        }

        fun delete(column: String, string: String) {
            val db = this.writableDatabase

            val selection = "$column LIKE ?"
            val selectionArgs = arrayOf(string)

            // DB delete 하지만 추후에 참조무결성 해결해야함.
            db.delete(PatientDataDB.TABLE_NAME, selection, selectionArgs)
        }

        fun select(projection: Array<String>?, isAnd: Boolean, columns: Array<String>?,
                   selectionArgs: Array<String>?, sort: String? = null) : Array<PatientData> {
            if (columns?.size != selectionArgs?.size)
                throw Exception("Parameter size is not matching")

            val db = this.readableDatabase

            val project: Array<String>
            val selection: String?
            val data: MutableList<PatientData> = ArrayList()

            if (projection == null) {
                project = arrayOf(
                    PatientDataDB.COLUMN_CLINIC_ID,
                    PatientDataDB.COLUMN_PATIENT_NAME,
                    PatientDataDB.COLUMN_USER_ID,
                    PatientDataDB.COLUMN_TASK_NO,
                        PatientDataDB.COLUMN_DISEASE_TYPE)
            }
            else
                project = projection

            if (isAnd)
                selection = columns?.joinToString(" = ? AND ")?.plus(" = ?")
            else
                selection = columns?.joinToString(" = ? OR ")?.plus(" = ?")

            val sorting = sort?.plus(" DESC")

            db.query (
                PatientDataDB.TABLE_NAME,
                project,
                selection,
                selectionArgs,
                null,
                null,
                sorting
            ).run {
                while (moveToNext()) {
                    data.add(data.size, PatientData(
                        getString(getColumnIndexOrThrow(PatientDataDB.COLUMN_CLINIC_ID)),
                        getString(getColumnIndexOrThrow(PatientDataDB.COLUMN_PATIENT_NAME)),
                        getString(getColumnIndexOrThrow(PatientDataDB.COLUMN_USER_ID)),
                        getString(getColumnIndexOrThrow(PatientDataDB.COLUMN_TASK_NO)).toInt(),
                            getString(getColumnIndexOrThrow(PatientDataDB.COLUMN_DISEASE_TYPE))
                    ).apply {
                        //id = getLong(getColumnIndexOrThrow(BaseColumns._ID)).toInt()
                    })
                }
                this.close()
            }
            return data.toTypedArray()
        }

        fun update(column: String, string: String, newValue: String) {
            val db = this.writableDatabase

            val values = ContentValues().apply {
                put(column, newValue)
            }
            val selection = "$column LIKE ?"
            val selectionArgs = arrayOf(string)

            db.update(
                PatientDataDB.TABLE_NAME,
                values,
                selection,
                selectionArgs
            )
        }

        fun closeDB() {
            this.close()
        }
    }
}
