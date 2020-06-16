package com.example.testemployees.api;

import com.example.testemployees.pojo.Employee;
import com.example.testemployees.pojo.EmployeeResponse;

import io.reactivex.Observable;

import retrofit2.http.GET;

//observable для того чтобы могли слушать события
// в кавычках указываем end point (это что в конце перед\)

public interface ApiService {
    @GET("testTask.json")
    Observable<EmployeeResponse> getEmployees();

}
