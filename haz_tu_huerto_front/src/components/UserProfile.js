import React, { Suspense } from "react";
import { useEffect, useState } from "react";
import { useLocation } from 'wouter';
import NavBar from './NavBar.js'
import '../styles/Profile.css'
import Picture from './Picture'
import { Helmet } from "react-helmet";
import UploadFile from './UploadFile'
import Swal from "sweetalert2";
import { Link } from 'wouter';
import CreateQuestion from './modals/CreateQuestion'
import Logout from "./Logout.js";

async function changePassword(credentials) {
    return fetch("http://localhost:8080/user/changePassword", {
      method: "PUT",
      headers: {
        "Content-Type": "application/json",
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem('token')}`,
        "Access-Control-Allow-Origin": "*"
      },
      body: JSON.stringify(credentials),
    }).then((data) => data.json());

}
 function deleteQuest(id) {
    console.log(id)
    fetch(`http://localhost:8080/question/${id}`, {
      method: "DELETE",
      headers: {
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem('token')}`
      }
    })

}
function deleteAns(id) {
    console.log(id)
    fetch(`http://localhost:8080/answer/${id}`, {
      method: "DELETE",
      headers: {
        "Accept": "application/json",
        "Authorization": `Bearer ${localStorage.getItem('token')}`
      }
    })

}

    

export default function UserProfile() {
    const [location, setLocation] = useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [user, setUser] = useState([])
    const [questions, setQuestions] = useState([])
    const [answers, setAnswers] = useState([])
    const [avatar, setAvatar]=useState('monke2.jpg')
    const name = location.split('/').reverse()[0]
    console.log(name)
    const apiUrl = `http://localhost:8080/user/${name}`

    const [oldPassword, setOldPassword] = useState();
    const [newPassword, setNewPassword] = useState();
    const [newVerifyPassword, setVerifyNewPassword] = useState();
  
    const handleSubmit = async (e) => {
      console.log(e)
      e.preventDefault();
      const response = await changePassword({
        oldPassword,
        newPassword,
        newVerifyPassword
      });
      if ("id" in response) {
        console.log(response);
        Swal.fire({
          icon: 'success',
          title: 'oleeee',
          text: 'Welcome',
          showConfirmButton: false,
          timer: 2000,
    
        });
      } else {
        console.log(response);
        console.log(response.status);
        Swal.fire({
          icon: 'error',
          title: 'Oops...',
          text: response.message,
        })
      }
    };
    async function getUser(){
        return fetch(apiUrl, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            }
        }).then(res => {
            console.log(res)
            if (res.ok) 
                return res.json()                
            throw new Error('Something went wrong');
        }).then((data) => {
            setUser(data)
            console.log(data)
            setQuestions(data.publishedQuestions)
            console.log(data.publishedQuestions)
            setAnswers(data.publishedAnswers)
            setAvatar(data.avatar)
            console.log(data.avatar)
            setIsLoading(false)
            
        }).catch((error) => {
            console.log(error)
            setLocation('/Page404')
        });
       
    }
    useEffect(function () {       
        //getUser()     
        fetch(apiUrl, {
                method: 'GET',
                headers: {
                    "Content-Type": "application/json",
                    "Accept": "application/json",
                    "Authorization": `Bearer ${localStorage.getItem('token')}`
                }
            }).then(res => {
                console.log(res)
                if (res.ok) {
                    return res.json()
                }                                    
                throw new Error('Something went wrong');
            }).then((data) => {
                setUser(data)
                console.log(data)
                setQuestions(data.publishedQuestions)
                console.log(data.publishedQuestions)
                setAnswers(data.publishedAnswers)
                setAvatar(data.avatar)
                console.log(data.avatar)
                setIsLoading(false)
                
            }).catch((error) => {
                console.log(error)
                setLocation('/Page404')
            });
           
        }, [])
    function size(list) {
        console.log(list)
        /*
        if (list == 'undefined'){
            return '?'
        }            
        return list.length
        */
       return '?'
    }

    if (isLoading) {
        return (
            <div clasName="App">
                <h1>Cargando...</h1>
            </div>
        );
    }
    return (
        <div>
            <NavBar></NavBar>
            {/* 
            <p>User Name: {user.username}</p>
            <p>Full Name: {user.fullName}</p>
            <p>Reputation: {user.reputation}</p>         
             */}
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossOrigin="anonymous" />
            <Helmet>
                <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            </Helmet>
            <div className="container">
                <div className="row profile">
                    <div className="col-md-3">
                        <div className="profile-sidebar">

                            <div className="profile-userpic">
                                <Picture keyword={avatar}></Picture>
                            </div>
                            <div className="profile-usertitle">
                                <div className="profile-usertitle-name">
                                    {user.username}
                                </div>
                                <div className="profile-usertitle-job">
                                    {user.fullName}
                                </div>
                            </div>




                            <div className="portlet light bordered">

                                <div className="row list-separated profile-stat">
                                    <div className="col-md-4 col-sm-4 col-xs-6">
                                        <div className="uppercase profile-stat-title"> {user.reputation} </div>
                                        <div className="uppercase profile-stat-text"> Reputation </div>
                                    </div>
                                    <div className="col-md-4 col-sm-4 col-xs-6">
                                        <div className="uppercase profile-stat-title"> {size(questions)} </div>
                                        <div className="uppercase profile-stat-text"> Questions </div>
                                    </div>
                                    <div className="col-md-4 col-sm-4 col-xs-6">
                                        <div className="uppercase profile-stat-title"> {size(user.publishedAnswers)}</div>
                                        <div className="uppercase profile-stat-text"> Answers </div>
                                    </div>
                                </div>

                                <div>

                                    <div className="margin-top-20 profile-desc-link">
                                        <i className="fa fa-globe"></i>
                                        <a href={user.email}>{user.email}</a>
                                    </div>
                                    

                                </div>
                                
                            </div>

                            <div>

                                    <div className="margin-top-20 profile-desc-link">
                                        <Logout></Logout>
                                    </div>
                                    

                                </div>

                        </div>
                    </div>
                    <div className="col-md-9">
                        <div className="profile-content">
                            <nav>
                                <div className="nav nav-tabs" id="nav-tab" role="tablist">
                                    <a className="nav-item nav-link active" id="nav-Question-tab" data-toggle="tab" href="#nav-Question" role="tab" aria-controls="nav-Question" aria-selected="true">Questions</a>
                                    <a className="nav-item nav-link" id="nav-Answer-tab" data-toggle="tab" href="#nav-Answer" role="tab" aria-controls="nav-Answer" aria-selected="false">Answers</a>
                                    <a className="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Upload Profile</a>
                                    <a className="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-password" role="tab" aria-controls="nav-password" aria-selected="false">Change Password</a>

                                </div>
                            </nav>
                            <div className="tab-content text-dark" id="nav-tabContent">
                                <div className="tab-pane fade show active " id="nav-Question" role="tabpanel" aria-labelledby="nav-Question-tab">
                                    <CreateQuestion></CreateQuestion>
                                    {
                                        <div className="courses-container">
                                            {questions.map(
                                                (question) => (
                                                    console.log(question),
                                                    (
                                                        <div className="course" key={question.id}>
                                                            <div className="course-preview">
                                                                <h6>{question.publisher}</h6>
                                                                <h3>{question.title}</h3>
                                                                <p>Score:</p>

                                                            </div>
                                                            <div className="course-info">
                                                                <div className="progress-container">
                                                                    <span class="progress-text">
                                                                        {question.createdAt}
                                                                    </span>
                                                                </div>

                                                                <h6>{question.content}</h6>
                                                                <button className="btn" onClick={()=>deleteQuest(question.id)}>Delete</button>


                                                            </div>
                                                        </div>
                                                    )
                                                )
                                            )}
                                        </div>
                                    }
                                </div>
                                <div className="tab-pane fade" id="nav-Answer" role="tabpanel" aria-labelledby="nav-Answer-tab">
                                    {
                                        <div className="courses-container">
                                            {
                                                answers.map(
                                                    (answer) => (
                                                        console.log(answer),
                                                        (

                                                            <div className="course" key={answer.id}>

                                                                <div className="course-info">
                                                                    <div className="progress-container">
                                                                        <h6>{answer.publisher}</h6>
                                                                        <span class="progress-text">
                                                                            {answer.createdAt}
                                                                        </span>
                                                                    </div>

                                                                    <h6>{answer.content}</h6>
                                                                    <button className="btn" onClick={()=>deleteAns(answer.id)}>Delete</button>

                                                                </div>

                                                            </div>
                                                        )
                                                    )
                                                )



                                            }
                                        </div>
                                    }
                                </div>

                                <div className="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab">
                                    <UploadFile></UploadFile>
                                </div>
                                <div className="tab-pane fade" id="nav-password" role="tabpanel" aria-labelledby="nav-password-tab">
                                    <form onSubmit={handleSubmit}>
                                        <div className="user-box">
                                            <input type="password" placeholder="Old Password" id="logUsername" required name="username" autoComplete="on" onChange={(e) => setOldPassword(e.target.value)}></input>
                                           
                                        </div>
                                        <div className="user-box">
                                            <input type="password" placeholder="New Password" id="logPassword" required name="password" autoComplete="on" onChange={(e) => setNewPassword(e.target.value)} ></input>
                                            
                                        </div>
                                        <div className="user-box">
                                            <input type="password" placeholder="Verify New Password" id="logVerifyPassword" required name="password" autoComplete="on" onChange={(e) => setVerifyNewPassword(e.target.value)} ></input>
                                            
                                        </div>
                                        <button type="submit">
                                            <span></span>
                                            <span></span>
                                            <span></span>
                                            <span></span>
                                            Submit
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>




    )
}