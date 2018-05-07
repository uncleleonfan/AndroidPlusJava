package com.leon.androidplus.di;

import com.leon.androidplus.data.AnswerDataSource;
import com.leon.androidplus.data.ArticleDataRepository;
import com.leon.androidplus.data.ArticleDataSource;
import com.leon.androidplus.data.AnswerDataRepository;
import com.leon.androidplus.data.CommentDataRepository;
import com.leon.androidplus.data.CommentDataSource;
import com.leon.androidplus.data.QuestionDataRepository;
import com.leon.androidplus.data.QuestionDataSource;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;

@Module
public interface DataRepositoryModule {

    @Singleton
    @Binds
    ArticleDataSource bindsArticleDataSource(ArticleDataRepository articleDataRepository);

    @Singleton
    @Binds
    AnswerDataSource bindsAnswerDataSource(AnswerDataRepository answerDataRepository);

    @Singleton
    @Binds
    QuestionDataSource bindsQuestionDataSource(QuestionDataRepository questionDataRepository);

    @Singleton
    @Binds
    CommentDataSource bindsCommentDataSource(CommentDataRepository commentDataRepository);

}
