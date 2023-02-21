import React from "react";
import { useEffect, useState } from "react";
import { useLocation } from "wouter";
import NavBar from "./NavBar.js";
import "../styles/List.css";
import {Link} from 'wouter';



export default function UserList() {
  const [location, setLocation] = useLocation();
  const [isLoading, setIsLoading] = useState(true);
  const [page, setPage] = useState([]);
  const name = location.split("/").reverse()[0];
  console.log(name);
  const apiUrl = `http://localhost:8080/`;
  const [img, setImg] = useState();

  async function fetchImage(name){
    const res = await fetch(apiUrl+'download/'+name, {
        method: "GET",
        headers: {
          "Content-Type": "image/jpeg",
          Accept: "application/json",
          Authorization: `Bearer ${localStorage.getItem("token")}`,
        }
    });
    const imageBlob = await res.blob();
    const imageObjectURL = URL.createObjectURL(imageBlob);
    setImg(imageObjectURL);
    return img;
  };

  useEffect(function () {
    fetch(apiUrl+'user', {
      method: "GET",
      headers: {
        "Content-Type": "application/json",
        Accept: "application/json",
        Authorization: `Bearer ${localStorage.getItem("token")}`,
      },
    })
      .then((res) => {
        if (res.ok) {
          return res.json();
        }
        throw new Error("Something went wrong");
      })
      .then((data) => {
        setPage(data.content);
        console.log(data.content);
      })
      .catch((error) => {
        console.log(error);
        setLocation("/Page404");
      });   
      
    setIsLoading(false);


  }, []);
 




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
           {
        <div className="courses-container">
          {page.map(
            (user) => (
              
              (    
                <div className="course" key={user.id}>
                  <div className="course-preview">
                    <img src={fetch(`http://localhost:8080/download/${user.avatar}`,{
                        method: "GET",
                        headers: {
                          "Content-Type": "image/jpeg",
                          "Accept": "*/*",
                          "Authorization": `Bearer ${localStorage.getItem("token")}`,
                        }
                    }).then((res) => {
                        console.log(res  )
                        if (res.ok) {           
                            console.log(URL.createObjectURL(res.blob()))                 
                            return  URL.createObjectURL(res.blob());                          
                        }
                        throw new Error("Something went wrong");
                      }).catch((error) => {
                        console.log(error);
                        setLocation("/user/Programer12");
                      })   
                    }></img>
                  </div>
                  <div className="course-info">
                    <h6>{user.fullName}</h6>
                    <h2>{user.username}</h2>
                    <p>User since: {user.createdAt}</p>
                    <button className="btn"><Link to={`/user/${user.username}`}>Profile</Link></button>
                  </div>
                </div>
              )
            )
          )}
        </div>
      }
    </div>
  );
}
