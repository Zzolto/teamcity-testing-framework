package com.example.teamcity.api;

import org.testng.annotations.Test;
import static io.qameta.allure.Allure.step;

public class BuildConfigurationTest extends BaseApiTest{

    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest(){
        step("Create User");
        step("Create project by user");
        step("Create buildType for project by user");
        step("Check buildTYpe was created successfully with correct data");
    }

    @Test(description = "User should not be able to create build type with same id", groups = {"Negative", "CRUD"})
    public void userCreateTwoBuildBuildTypesWithSameIdTest(){
        step("Create User");
        step("Create project by user");
        step("Create buildType1 for project by user");
        step("Create buildType2 for project by user with same id");
        step("Check buildTYpe was not created with bad request code");
    }

    @Test(description = "Project admin should be able to create build type fr their project", groups = {"Positive", "Roles"})
    public void projectAdminCreateBuildTypeTest(){
        step("Create user");
        step("Create project");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project by user");
        step("Check buildTYpe was created successfully");
    }

    @Test(description = "Project admin should be able to create build type fr their project", groups = {"Negative", "Roles"})
    public void projectAdminCreatesBuildTypeForAnotherUserProjectTest(){
        step("Create user1");
        step("Create project1");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create user2");
        step("Create project2");
        step("Grant user PROJECT_ADMIN role in project");

        step("Create buildType for project1 by user2");
        step("Check buildType was not created with forbidden code");
    }
}
