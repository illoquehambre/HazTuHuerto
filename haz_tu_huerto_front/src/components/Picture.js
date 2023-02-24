  import React, { useEffect, useState } from "react";
  import { useLocation } from "wouter";
  export default function Picture({ keyword }) {
      const apiUrl = `http://localhost:8080/download/`;
      const [location, setLocation] = useLocation();
      const [isLoading, setIsLoading] = useState(true);
      const [img, setImg] = useState();

      async function fetchImage(name) {
          /* if(keyword='undefined'){
              setIsLoading(false)
              return defaultPic
          }
              
  */
          const res = await fetch(apiUrl + name, {
              method: "GET",
              headers: {
                  "Content-Type": "image/jpeg",
                "Accept": "application/json",
                "Authorization": `Bearer ${localStorage.getItem("token")}`,
              }
          });
          const imageBlob = await res.blob();
          const imageObjectURL = URL.createObjectURL(imageBlob);
          setImg(imageObjectURL);
          setIsLoading(false)
          return img;
      };


      useEffect(() => {
          fetchImage(keyword);
      }, [keyword]);


      /*
        useEffect(function () {
          fetch(apiUrl+keyword, {
            method: "GET",
            headers: {
              "Content-Type": "application/json",
              Accept: "application/json",
              Authorization: `Bearer ${localStorage.getItem("token")}`,
            },
          })
            .then((res) => {
              if (res.ok) {
                  console.log(res)
                return res;
              }
              throw new Error("Something went wrong");
            })
            .then((data) => {
              setImg(data);
              console.log(data);
            })
            .catch((error) => {
              console.log(error);
              setLocation("/Page404");
            });   
            
          setIsLoading(false);
      
      
        }, []);
      */




      if (isLoading) {
          return (
              <div className="App">
                  <h1>Cargando...</h1>
              </div>
          );
      }
      return (
          <img src={img}></img>
      );
  }