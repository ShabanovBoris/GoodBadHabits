//package com.practice.goodbadhabits.repository
//
//import com.practice.data.api.HabitApi
//import com.practice.data.api.HabitRemoteDataSource
//import com.practice.data.api.impl.HabitRemoteDataSourceImpl
//import com.practice.data.db.HabitDao
//import com.practice.data.db.HabitLocalDataSource
//import com.practice.data.db.impl.HabitLocalDataSourceImpl
//import com.practice.data.dto.HabitApiResponse
//import com.practice.data.dto.HabitUid
//import com.practice.data.mappers.HabitApiResponseMapper
//import com.practice.data.mappers.HabitEntityMapper
//import com.practice.data.repositories.habits.HabitRepositoryImpl
//import com.practice.domain.entities.Habit
//import com.practice.goodbadhabits.habit.getHabitMock
//import com.practice.goodbadhabits.habit.getHabitMockList
//import kotlinx.coroutines.Dispatchers
//import kotlinx.coroutines.ExperimentalCoroutinesApi
//import kotlinx.coroutines.flow.catch
//import kotlinx.coroutines.flow.collect
//import kotlinx.coroutines.flow.flowOf
//import kotlinx.coroutines.runBlocking
//import kotlinx.coroutines.test.TestCoroutineDispatcher
//import kotlinx.coroutines.test.resetMain
//import kotlinx.coroutines.test.runBlockingTest
//import kotlinx.coroutines.test.setMain
//import org.junit.After
//import org.junit.Assert.assertEquals
//import org.junit.Before
//import org.junit.Test
//import org.mockito.kotlin.*
//import retrofit2.HttpException
//import retrofit2.Response
//import java.net.UnknownHostException
//import java.util.concurrent.TimeoutException
//
//@ExperimentalCoroutinesApi
//class HabitRepositoryTest {
//
//    private val testDispatcher = TestCoroutineDispatcher()
//
//    @Before
//    fun setup() {
//        Dispatchers.setMain(testDispatcher)
//    }
//
//    @After
//    fun tearDown() {
//        Dispatchers.resetMain()
//        testDispatcher.cleanupTestCoroutines()
//    }
//
//    //  dependencies
//    private val dao: HabitDao = mock()
//    private val api: HabitApi = mock()
//
//    private val localDS: HabitLocalDataSource =
//        spy(HabitLocalDataSourceImpl(dao, Dispatchers.Main, HabitEntityMapper()))
//
//    private val remoteDS: HabitRemoteDataSource =
//        spy(HabitRemoteDataSourceImpl(api, HabitApiResponseMapper()))
//
//    private val repository = HabitRepositoryImpl(localDS, remoteDS)
//
//    @Test
//    fun `mapper from db working correct`() = runBlocking<Unit> {
//        //arrange
//        val habitList = getHabitMockList()
//        val habitEntityList = HabitEntityMapper().toHabitEntityList(habitList)
//
//        whenever(dao.getHabits()).thenReturn(flowOf(habitEntityList))
//        //act
//        var res = emptyList<Habit>()
//        repository.getHabitsCache().collect { res = it }
//        //assert
//        assertEquals(habitList, res)
//        verify(dao).getHabits()
//        verify(localDS).getHabits()
//    }
//
//    @Test
//    fun `mapper from network working correct`() = runBlocking<Unit> {
//        //arrange
//        val habitList = getHabitMockList()
//        val habitResponseList = HabitApiResponseMapper().toHabitApiResponseItemList(habitList)
//
//        whenever(api.getHabits()).thenReturn(habitResponseList)
//        //act
//        var res = emptyList<Habit>()
//        repository.fetchHabits().collect { res = it }
//        //assert
//        assertEquals(habitList, res)
//        verify(api).getHabits()
//        verify(remoteDS).fetchHabits()
//    }
//
//    @Test
//    fun `retrying connection to network first and 3 more times when cause failure request`() =
//        runBlockingTest {
//            //arrange
//            val httpEx: HttpException = mock()
//            whenever(api.getHabits())
//                .thenAnswer { throw TimeoutException() }
//                .thenAnswer { throw UnknownHostException() }
//                .thenAnswer { throw httpEx }
//            //act
//            repository.fetchHabits()
//                .catch {}
//                .collect()
//            //assert
//            verify(api, times(4)).getHabits()
//            verify(remoteDS).fetchHabits()
//        }
//
//
//    @Test
//    fun `return -1 after failure upload habit into network`() = runBlocking<Unit> {
//        whenever(api.putHabit(mock())).thenReturn(HabitUid(null))
//
//        val res = repository.uploadHabit(getHabitMock())
//
//        verify(remoteDS).uploadHabit(any())
//        assertEquals("-1", res)
//    }
//}