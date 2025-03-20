package com.example.teamcity.api;

import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.models.User;
import com.example.teamcity.api.requests.checked.CheckedBase;
import com.example.teamcity.api.spec.Specification;
import org.testng.annotations.Test;

import java.util.concurrent.atomic.AtomicReference;

import static com.example.teamcity.api.generators.TestDataGenerator.generate;
import static io.qameta.allure.Allure.step;

public class BuildConfigurationTest extends BaseApiTest{

    @Test(description = "User should be able to create build type", groups = {"Positive", "CRUD"})
    public void userCreatesBuildTypeTest(){
        var user = generate(User.class);

        step("Create User", () -> {
            var requester = new CheckedBase<User>(Specification.superUserSpec(), Endpoint.USERS);
            requester.create(user);
        });

        var project = generate(Project.class);
        AtomicReference<String> projectId = new AtomicReference<>("");

        step("Create project by user", () -> {
            var requester = new CheckedBase<Project>(Specification.authSpec(user), Endpoint.PROJECTS);
            projectId.set(requester.create(project).getId());
        });

        var requester = new CheckedBase<BuildType>(Specification.authSpec(user), Endpoint.BUILD_TYPES);
        AtomicReference<String> buildTypeId = new AtomicReference<>("");

        var buildType = generate(BuildType.class);
        buildType.setProject(Project.builder().id(projectId.get()).locator(null).build());

        step("Create buildType for project by user", () -> {
            buildTypeId.set(requester.create(buildType).getId());
        });

        step("Check buildTYpe was created successfully with correct data", () -> {
            var createdBuildType = requester.read(buildTypeId.get());
            softy.assertEquals(buildType.getName(), createdBuildType.getName(), "Error");
        });
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
