package com.alisasadkovska.czytanka.common

import java.io.File

@Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
object Utils {
     fun createNewFolder(folderName: String, path: String, callback: (result: Boolean, message: String) -> Unit){
         val folderAlreadyExists = File(path).listFiles().map { it.name }.contains(folderName)
         if (folderAlreadyExists)
             return
         else{
             val file = File(path, folderName)
             try {
                 val result = file.mkdir()
                 if (result){
                     callback(result, "Folder '${folderName}' created successfully.")
                 } else {
                     callback(result, "Unable to create folder '${folderName}'.")

                 }
             }catch (e: Exception) {
                 callback(false, "Unable to create folder. Please try again.")
                 e.printStackTrace()
             }
         }
     }
}