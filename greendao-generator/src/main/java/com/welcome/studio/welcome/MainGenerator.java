package com.welcome.studio.welcome;

import org.greenrobot.greendao.annotation.Entity;

import java.io.IOException;


public class MainGenerator {

    public static void main(String... args) throws Exception {
        Schema schema=new Schema(1,"com.welcome.studio.welcome");
        Entity user=schema.addEntity("User");
        Entity raiting=schema.addEntity("Raiting");
        Entity archivePhoto=schema.addEntity("ArchivePhoto");
//        user.addIdProperty().primaryKey();
//        user.addStringProperty("nickname");
//        user.addStringProperty("email");
//        user.addStringProperty("imei");
//        user.addStringProperty("photoRef").columnName("photo_ref");
////        user.addToOne(raiting,raiting.getPkProperty());
////        //user.addToMany(archivePhoto,archivePhoto.getPkProperty());
////        raiting.addIdProperty().primaryKey();
////        raiting.addLongProperty("likeCount").columnName("like_count");
////        raiting.addLongProperty("willcomeCount").columnName("willcome_count");
////        raiting.addIntProperty("postCount").columnName("post_count");
////        raiting.addIntProperty("vippostCount").columnName("vippost_count");
////        raiting.addToOne(user,user.getPkProperty(),"user_id");
////        archivePhoto.addStringProperty("photoRef").columnName("photo_ref");
////        archivePhoto.addToOne(user,user.getPkProperty());

        new DaoGenerator().generateAll(schema,"./app/src/main/java");


    }
}
