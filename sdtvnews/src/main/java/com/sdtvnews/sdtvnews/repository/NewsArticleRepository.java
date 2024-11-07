package com.sdtvnews.sdtvnews.repository;

import com.sdtvnews.sdtvnews.dto.ListArticleDTO;
import com.sdtvnews.sdtvnews.entity.NewsArticle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NewsArticleRepository extends JpaRepository<NewsArticle,Long> {

    @Query(value = "select \n" +
            "\tna.id,\n" +
            "    na.title,\n" +
            "    na.create_date,\n" +
            "    c.name,\n" +
            "    CONCAT(u.first_name, ' ', u.last_name) AS full_name\n" +
            "from news_article na\n" +
            "inner join category c on na.cate_id = c.id \n" +
            "inner join `user` u on na.create_by=u.id \n" +
            "order by na.create_date desc ",nativeQuery = true)
    List<ListArticleDTO>lstNewsArticle();

    @Query(value = "select\n" +
            "\t\tna.id,\n" +
            "\t\tna.title,\n" +
            "\t\tna.create_date,\n" +
            "\t\tc.name,\n" +
            "\t\tCONCAT(u.first_name, ' ', u.last_name) AS full_name\n" +
            "    from news_article na\n" +
            "    inner join category c on na.cate_id = c.id\n" +
            "    inner join `user` u on na.create_by=u.id\n" +
            "    where DATE(na.create_date) BETWEEN '2024-11-01' AND '2024-11-04'\n" +
            "    order by na.create_date desc",nativeQuery = true)
    List<ListArticleDTO>lstNewsArticleByCreateDate();


    @Query(value = "select\n" +
            "\t\tna.id,\n" +
            "\t\tna.title,\n" +
            "\t\tna.create_date,\n" +
            "\t\tc.name,\n" +
            "\t\tCONCAT(u.first_name, ' ', u.last_name) AS full_name\n" +
            "    from news_article na\n" +
            "    inner join category c on na.cate_id = c.id\n" +
            "    inner join `user` u on na.create_by=u.id\n" +
            "    where 1=1\n" +
            "    and c.id in (1,2,3)\n" +
            "    and DATE(na.create_date) BETWEEN '2024-11-01' AND '2024-11-04'\n" +
            "    order by na.create_date desc",nativeQuery = true)
    List<ListArticleDTO>lstNewsArticleByCategory();

    @Query(value = "select\n" +
            "\t\tna.id,\n" +
            "\t\tna.title,\n" +
            "\t\tna.create_date,\n" +
            "\t\tc.name,\n" +
            "\t\tCONCAT(u.first_name, ' ', u.last_name) AS full_name\n" +
            "    from news_article na\n" +
            "    inner join category c on na.cate_id = c.id\n" +
            "    inner join `user` u on na.create_by=u.id\n" +
            "    where 1=1\n" +
            "    and u.id in (1)\n" +
            "    and DATE(na.create_date) BETWEEN '2024-11-01' AND '2024-11-04'\n" +
            "    order by na.create_date desc",nativeQuery = true)
    List<ListArticleDTO>lstNewsArticleByUser();

    @Query(value = " select\n" +
            "\t\tna.id,\n" +
            "\t\tna.title,\n" +
            "\t\tna.create_date,\n" +
            "\t\tc.name,\n" +
            "\t\tCONCAT(u.first_name, ' ', u.last_name) AS full_name\n" +
            "    from news_article na\n" +
            "    inner join category c on na.cate_id = c.id\n" +
            "    inner join `user` u on na.create_by=u.id\n" +
            "    where 1=1\n" +
            "    and c.id in (1,2,3)\n" +
            "    and u.id in (1)\n" +
            "    and DATE(na.create_date) BETWEEN '2024-11-01' AND '2024-11-04'\n" +
            "    order by na.create_date desc",nativeQuery = true)
    List<ListArticleDTO>lstNewsArticleByUserAndCategory();



    boolean existsByTitle(String title);




}
