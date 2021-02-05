package com.astech.movsee.utils;

public class DataDummy {

    /*public static List<MovieEntity> getMoviesDummy(){

        ArrayList<MovieEntity> movies = new ArrayList<>();

        movies.add(new MovieEntity("11",
                "/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg",
                "6.1",
                false));

        movies.add(new MovieEntity("21",
                "/tI8ocADh22GtQFV28vGHaBZVb0U.jpg",
                "6",
                false));

        movies.add(new MovieEntity("31",
                "/qnlChF8U4diiykXQYs1miigGy7t.jpg",
                "6.9",
                false));

        movies.add(new MovieEntity("41",
                "/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg",
                "7.9",
                false));

        movies.add(new MovieEntity("51",
                "/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg",
                "7.9",
                false));

        return movies;
    }

    public static List<TvEntity> getTvsDummy(){

        ArrayList<TvEntity> tvs = new ArrayList<>();

        tvs.add(new TvEntity("12",
                "/apbrbWs8M9lyOpJYU5WXrpFbk1Z.jpg",
                "7",
                false));

        tvs.add(new TvEntity("22",
                "/6t6r1VGQTTQecN4V0sZeqsmdU9g.jpg",
                "5",
                false));

        tvs.add(new TvEntity("32",
                "/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg",
                "9",
                false));

        tvs.add(new TvEntity("42",
                "/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg",
                "3",
                false));

        tvs.add(new TvEntity("52",
                "/wcaDIAG1QdXQLRaj4vC1EFdBT2.jpg",
                "3",
                false));

        return tvs;
    }

    public static MovieDetailEntity getMovieDetailDummy(String catalogId){
        return new MovieDetailEntity(catalogId,
                "Ad Astra",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "2019-09-17",
                "Science Fiction\nDrama",
                "123");
    }

    public static TvDetailEntity getTvDetailDummy(String catalogId){
        return new TvDetailEntity(catalogId,
                "The Flash",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "2019-09-17",
                "Science Fiction\nDrama",
                "123");
    }

    public static VideoEntity getVideoDummy(String catalogId){
            VideoEntity videoEntity = new VideoEntity();
            videoEntity.setVideo("P6AaSMfXHbA");
            return videoEntity;
    }

    public static ArrayList<CatalogResponse> getRemoteMoviesDummy(){

        ArrayList<CatalogResponse> movies = new ArrayList<>();
        CatalogResponse data1 = new CatalogResponse();
        data1.setCatalogId("11");
        data1.setPoster("/xBHvZcjRiWyobQ9kxBhO6B2dtRI.jpg");
        data1.setScore("6.1");
        movies.add(data1);

        CatalogResponse data2 = new CatalogResponse();
        data1.setCatalogId("21");
        data1.setPoster("/tI8ocADh22GtQFV28vGHaBZVb0U.jpg");
        data1.setScore("6");
        movies.add(data2);

        CatalogResponse data3 = new CatalogResponse();
        data1.setCatalogId("31");
        data1.setPoster("/qnlChF8U4diiykXQYs1miigGy7t.jpg");
        data1.setScore("6.9");
        movies.add(data3);

        CatalogResponse data4 = new CatalogResponse();
        data1.setCatalogId("41");
        data1.setPoster("/aQvJ5WPzZgYVDrxLX4R6cLJCEaQ.jpg");
        data1.setScore("7.9");
        movies.add(data4);

        CatalogResponse data5 = new CatalogResponse();
        data1.setCatalogId("51");
        data1.setPoster("/7IiTTgloJzvGI1TAYymCfbfl3vT.jpg");
        data1.setScore("7.9");
        movies.add(data5);

        return movies;
    }

    public static ArrayList<CatalogResponse> getRemoteTvsDummy(){

        ArrayList<CatalogResponse> tvs = new ArrayList<>();

        CatalogResponse data1 = new CatalogResponse();
        data1.setCatalogId("12");
        data1.setPoster("/apbrbWs8M9lyOpJYU5WXrpFbk1Z.jpg");
        data1.setScore("7");
        tvs.add(data1);

        CatalogResponse data2 = new CatalogResponse();
        data1.setCatalogId("22");
        data1.setPoster("/6t6r1VGQTTQecN4V0sZeqsmdU9g.jpg");
        data1.setScore("5");
        tvs.add(data2);

        CatalogResponse data3 = new CatalogResponse();
        data1.setCatalogId("32");
        data1.setPoster("/drlfSCIlMKrEeMPhi8pqY4xGxj.jpg");
        data1.setScore("9");
        tvs.add(data3);

        CatalogResponse data4 = new CatalogResponse();
        data1.setCatalogId("42");
        data1.setPoster("/wHa6KOJAoNTFLFtp7wguUJKSnju.jpg");
        data1.setScore("3");
        tvs.add(data4);

        CatalogResponse data5 = new CatalogResponse();
        data1.setCatalogId("52");
        data1.setPoster("/wcaDIAG1QdXQLRaj4vC1EFdBT2.jpg");
        data1.setScore("3");
        tvs.add(data5);

        return tvs;
    }

    public static DetailResponse getRemoteDetailDummy(String catalogId){
        DetailResponse data = new DetailResponse();
        data.setCatalogId(catalogId);
        data.setTitle("Ad Astra");
        data.setOverview("The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.");
        data.setRelease("2019-09-17");
        data.setGenre("Science Fiction" +"\n" + "Drama");
        data.setMinute("123");
        return data;
    }

    public static VideoResponse getRemoteVideoDummy(String catalogId){
        VideoResponse videoResponse = new VideoResponse();
        videoResponse.setCatalogId(catalogId);
        videoResponse.setVideo("P6AaSMfXHbA");
        return videoResponse;
    }

    public static MovieWithDetail generateDummyMovieWithDetail(MovieEntity movieEntity,boolean state){
        MovieWithDetail movieWithDetail = new MovieWithDetail();
        movieWithDetail.movieEntity = movieEntity;
        movieWithDetail.movieEntity.setFavorite(state);
        movieWithDetail.movieDetailEntity = getMovieDetailDummy(movieEntity.getCatalogId());

        return movieWithDetail;
    }

    public static TvWithDetail generateDummyTvWithDetail(TvEntity tvEntity, boolean state){
        TvWithDetail tvWithDetail = new TvWithDetail();
        tvWithDetail.tvEntity = tvEntity;
        tvWithDetail.tvEntity.setFavorite(state);
        tvWithDetail.tvDetailEntity = getTvDetailDummy(tvEntity.getCatalogId());

        return tvWithDetail;
    }

    public static MovieDetailEntity getDummyMovieVideo(String catalogId){
        MovieDetailEntity detailEntity = new MovieDetailEntity(catalogId,
                "Ad Astra",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "2019-09-17",
                "Science Fiction\nDrama",
                "123");

        detailEntity.videoEntity = getVideoDummy(catalogId);
        return detailEntity;
    }

    public static TvDetailEntity getDummyTvVideo(String catalogId){
        TvDetailEntity detailEntity = new TvDetailEntity(catalogId,
                "The Flash",
                "The near future, a time when both hope and hardships drive humanity to look to the stars and beyond. While a mysterious phenomenon menaces to destroy life on planet Earth, astronaut Roy McBride undertakes a mission across the immensity of space and its many perils to uncover the truth about a lost expedition that decades before boldly faced emptiness and silence in search of the unknown.",
                "2019-09-17",
                "Science Fiction\nDrama",
                "123");

        detailEntity.videoEntity = getVideoDummy(catalogId);
        return detailEntity;
    }

     */

}
