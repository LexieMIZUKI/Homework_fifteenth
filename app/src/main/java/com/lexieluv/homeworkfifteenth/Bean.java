package com.lexieluv.homeworkfifteenth;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "bean_tb")
public class Bean {
        /**
         * title : Oracle数据库开发必备利器之PL/SQL基础
         * author : Collen7788
         * content : PL/SQL是Oracle数据库对SQL语句的扩展，是存储过程和自定义函数的高级内容学习的必备基础。本门课程为大家带来PL/SQL的基本语法、光标和例外的概念，并通过案例演示了PL/SQL开发的过程。
         */
        @DatabaseField(useGetSet = true,generatedId = true,columnName = "id")
        private int id;

        @DatabaseField(useGetSet = true,columnName = "title")
        private String title;

        @DatabaseField(useGetSet = true,columnName = "author")
        private String author;

        @DatabaseField(useGetSet = true,columnName = "content")
        private String content;

        public Bean(){}

        public Bean(String title, String author, String content) {
            this.title = title;
            this.author = author;
            this.content = content;
        }

    public int getId(){return id;}

        public void setId(int id){this.id = id;}

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

    @Override
    public String toString() {
        return "Bean{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bean bean = (Bean) o;

        if (id != bean.id) return false;
        if (title != null ? !title.equals(bean.title) : bean.title != null) return false;
        if (author != null ? !author.equals(bean.author) : bean.author != null) return false;
        return content != null ? content.equals(bean.content) : bean.content == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        return result;
    }
}
