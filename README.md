# SEG2105_GROUP2_V2
Second Repository for Group 20 in Spring Semester 2021, repository created to recover project

Members of Group20:

Naomi Halder 300041321

Huzaifa Nissare-Houssan 3001721686

Susan Peters 300009003

Simon Proulx 300067852


Adminstrator extends Activity, User{

	//display menu activties 


	click users{
	
	StartActivity --> UserList

	}

    click homeIcon{

        startActivity -- > MainActivityWelcome
    }



}


CourseList extends Activity{

    //course item needs to have a delete button, edit button and ">" as direction to open course page, on cardview
    //courseList activity needs to have a backbutton 

    onclick Course{
        startActivity -- > course page
    }

    onclick Delete{
        deleteCourse(c: Course)
    }

    onclick editCourse{
        editCourse(course Original, course New)
    }

    onclick addButton{
        addCourse(c: Course);
    }

    display Courses(){
        call method in DBHandlerCourses
        //handle with adapter
    }


}

UserList extends Activity{

    //courseList activity needs to have a backbutton 
    //user item needs to have a delete button on cardview

    onclick delete{
        deleteUser()
    }

    displayUsers(){
        call method in DBHandlerUsers
        handle with adapter
    }

}

