import React, { Suspense } from "react";
import { useEffect, useState } from "react";
import { useLocation } from 'wouter';
import NavBar from './NavBar.js'
import '../styles/Profile.css'
import Picture from './Picture'
import { Helmet } from "react-helmet";
import UploadFile from './UploadFile'
import {Link} from 'wouter';
export default function UserProfile() {
    const [location, setLocation] = useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [user, setUser] = useState([])
    const [questions, setQuestions] = useState([])
    const name = location.split('/').reverse()[0]
    console.log(name)
    const apiUrl = `http://localhost:8080/user/${name}`

    useEffect(function () {
        fetch(apiUrl, {
            method: 'GET',
            headers: {
                "Content-Type": "application/json",
                "Accept": "application/json",
                "Authorization": `Bearer ${localStorage.getItem('token')}`
            }
        })
            .then(res => {

                if (res.ok) {
                    return res.json();
                }
                throw new Error('Something went wrong');
            })
            .then((data) => {
                setUser(data)
                setQuestions(data.publishedQuestions)
                console.log(data)
                console.log(data.publishedQuestions)
            })
            .catch((error) => {
                console.log(error)
                setLocation('/Page404')
            });
        setIsLoading(false)



    }, [])
    function size(list) {
        if (list = 'undefined')
            return '?'
        return list.length
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
                                <Picture keyword={user.avatar}></Picture>
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



                        </div>
                    </div>
                    <div className="col-md-9">
                        <div className="profile-content">
                            <nav>
                                <div className="nav nav-tabs" id="nav-tab" role="tablist">
                                    <a className="nav-item nav-link active" id="nav-Question-tab" data-toggle="tab" href="#nav-Question" role="tab" aria-controls="nav-Question" aria-selected="true">Questions</a>
                                    <a className="nav-item nav-link" id="nav-Answer-tab" data-toggle="tab" href="#nav-Answer" role="tab" aria-controls="nav-Answer" aria-selected="false">Answers</a>
                                    <a className="nav-item nav-link" id="nav-new-tab" data-toggle="tab" href="#nav-new" role="tab" aria-controls="nav-new" aria-selected="false">New Quest</a>
                                    <a className="nav-item nav-link" id="nav-profile-tab" data-toggle="tab" href="#nav-profile" role="tab" aria-controls="nav-profile" aria-selected="false">Upload Profile</a>
                                </div>
                            </nav>
                            <div className="tab-content text-dark" id="nav-tabContent">
                                <div className="tab-pane fade show active " id="nav-Question" role="tabpanel" aria-labelledby="nav-Question-tab">
                                    {
                                        <div className="courses-container">
                                            {questions.map(
                                                (question) => (
                                                    console.log(question),
                                                    (
                                                        <div className="course" key={question.id}>
                                                            <div className="course-preview">
                                                                <h6>{question.publisher.username}</h6>
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
                                                                <button className="btn"><Link to={`/question/${question.id}`}>Ask Question</Link></button>


                                                            </div>
                                                        </div>
                                                    )
                                                )
                                            )}
                                        </div>
                                    }
                                </div>
                                <div className="tab-pane fade" id="nav-Answer" role="tabpanel" aria-labelledby="nav-Answer-tab">...</div>
                                <div className="tab-pane fade" id="nav-new" role="tabpanel" aria-labelledby="nav-new-tab">...</div>
                                <div className="tab-pane fade" id="nav-profile" role="tabpanel" aria-labelledby="nav-profile-tab"><UploadFile id={user.id}></UploadFile></div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>


        </div>




    )
}