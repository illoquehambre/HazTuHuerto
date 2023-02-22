import React from "react";
import { useEffect, useState } from "react";
import { useLocation } from 'wouter';
import NavBar from './NavBar.js'
import { Link } from 'wouter';
import "../styles/List.css";
import AskQuestion from './modals/AskQuesquion'


export default function QuestionDetails() {
    const [location, setLocation] = useLocation();
    const [isLoading, setIsLoading] = useState(true);
    const [question, setQuestion] = useState({})
    const [answers, setAnswers] = useState([])
    const name = location.split('/').reverse()[0]
    console.log(name)
    const apiUrl = `http://localhost:8080/question/${name}`

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
                setQuestion(data)
                setAnswers(data.answers)
                console.log(data)
            })
            .catch((error) => {
                console.log(error)
                setLocation('/Page404')
            });
        setIsLoading(false)

    }, [])


    if (isLoading) {
        return (
            <div className="App">
                <h1>Cargando...</h1>
            </div>
        );
    }
    return (
        <div>
            <NavBar></NavBar>


            <div className="courses-container">
                <div className="course">
                    <div className="course-preview">
                        <h6>{question.publisher}</h6>
                        <h3>{question.title}</h3>
                        <p>Score: {question.score}</p>

                    </div>
                    <div className="course-info">
                        <div className="progress-container">
                            <span className="progress-text">
                                {question.createdAt}
                            </span>
                        </div>

                        <h6>{question.content}</h6>
                        <AskQuestion id={question.id}></AskQuestion>


                    </div>
                </div>
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


                                    </div>

                                </div>
                            )
                        )
                    )



                }

            </div>

        </div>
    )


}