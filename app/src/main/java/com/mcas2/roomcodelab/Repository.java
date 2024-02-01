package com.mcas2.roomcodelab;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.mcas2.roomcodelab.daos.PictureDao;
import com.mcas2.roomcodelab.daos.WordDao;
import com.mcas2.roomcodelab.entities.Picture;
import com.mcas2.roomcodelab.entities.Word;

import java.util.List;

public class Repository {
    private WordDao wordDao;
    private PictureDao pictureDao;
    private LiveData<List<Word>> allWords;
    private LiveData<List<Picture>> allPictures;

    Repository(Application application) {
        MyRoomDatabase db = MyRoomDatabase.getDatabase(application);
        wordDao = db.wordDao();
        pictureDao = db.pictureDao();
        allWords = wordDao.getOrderedWords();
        allPictures = pictureDao.getPictures();
    }

    LiveData<List<Word>> getAllWords() {
        return allWords;
    }
    LiveData<List<Picture>> getAllPictures() {
        return allPictures;
    }

    void insertWord(Word word) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            wordDao.insert(word);
        });
    }

    void insertPicture(List<Picture> pictures) {
        MyRoomDatabase.databaseWriteExecutor.execute(() -> {
            pictureDao.insert(pictures);
        });
    }
}
