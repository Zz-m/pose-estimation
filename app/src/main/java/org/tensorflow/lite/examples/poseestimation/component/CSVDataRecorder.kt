package org.tensorflow.lite.examples.poseestimation.component

import android.R.attr.name
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import org.tensorflow.lite.examples.poseestimation.data.BodyPart
import org.tensorflow.lite.examples.poseestimation.data.KeyPoint
import org.tensorflow.lite.examples.poseestimation.data.Person
import java.io.BufferedWriter
import java.io.File
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.OutputStream
import java.io.OutputStreamWriter
import java.time.LocalDateTime
import java.util.Objects


/**
 *Created by dhx on 2024/2/19
 */
class CSVDataRecorder {

    private val HEADERS = arrayOf(
        "PERSON_SCORE",
        "NOSE_x", "NOSE_y",
        "LEFT_EYE_x", "LEFT_EYE_y",
        "RIGHT_EYE_x", "RIGHT_EYE_y",
        "LEFT_EAR_x", "LEFT_EAR_y",
        "RIGHT_EAR_x", "RIGHT_EAR_y",
        "LEFT_SHOULDER_x", "LEFT_SHOULDER_y",
        "RIGHT_SHOULDER_x", "RIGHT_SHOULDER_y",
        "LEFT_ELBOW_x", "LEFT_ELBOW_y",
        "RIGHT_ELBOW_x", "RIGHT_ELBOW_y",
        "LEFT_WRIST_x", "LEFT_WRIST_y",
        "RIGHT_WRIST_x", "RIGHT_WRIST_y",
        "LEFT_HIP_x", "LEFT_HIP_y",
        "RIGHT_HIP_x", "RIGHT_HIP_y",
        "LEFT_KNEE_x", "LEFT_KNEE_y",
        "RIGHT_KNEE_x", "RIGHT_KNEE_y",
        "LEFT_ANKLE_x", "LEFT_ANKLE_y",
        "RIGHT_ANKLE_x", "RIGHT_ANKLE_y",
    )

    private lateinit var fileWriter:BufferedWriter

    constructor(context: Context) {

        val fos: OutputStream?
        val fileName = "pose_est_" + LocalDateTime.now().toString() + ".csv"
        fos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val resolver: ContentResolver = context.contentResolver
            val contentValues = ContentValues()
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "text/csv")
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOWNLOADS)
            val fileUri = resolver.insert(MediaStore.Downloads.EXTERNAL_CONTENT_URI, contentValues)
            resolver.openOutputStream(fileUri!!)
        } else {
            val imagesDir =
                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    .toString()
            val targetFile = File(imagesDir, fileName)
            FileOutputStream(targetFile)
        }

        fileWriter = BufferedWriter(OutputStreamWriter(fos))
        val sb = StringBuilder()
        for (s in HEADERS) {
            sb.append(s).append(',')
        }
        if(sb.isNotEmpty()){
            sb.deleteCharAt(sb.length - 1);
        }
        sb.append('\n')
        fileWriter.append(sb)
    }

    public fun append(person:Person){
        val sb = StringBuilder()
        sb.append(person.score).append(',')
        val pointMap = HashMap<BodyPart, KeyPoint>()
        for (kp in person.keyPoints) {
            pointMap[kp.bodyPart] = kp
        }
        sb.append(pointMap[BodyPart.NOSE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.NOSE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_EYE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_EYE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_EYE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_EYE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_EAR]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_EAR]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_EAR]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_EAR]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_SHOULDER]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_SHOULDER]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_SHOULDER]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_SHOULDER]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_ELBOW]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_ELBOW]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_ELBOW]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_ELBOW]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_WRIST]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_WRIST]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_WRIST]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_WRIST]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_HIP]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_HIP]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_HIP]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_HIP]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_KNEE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_KNEE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_KNEE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_KNEE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_ANKLE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.LEFT_ANKLE]?.coordinate?.y?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_ANKLE]?.coordinate?.x?:"NULL")
        sb.append(pointMap[BodyPart.RIGHT_ANKLE]?.coordinate?.y?:"NULL")
        sb.append('\n')
        fileWriter.append(sb)
    }

    public fun close() {
        fileWriter.flush()
        fileWriter.close()
    }


}