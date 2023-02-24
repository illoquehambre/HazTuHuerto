import React from "react";
import { Link } from 'wouter';
import Signup from "./Signup";
import Login from "./Login";
import { Helmet } from "react-helmet";

export default function PokemonDetails() {
    return (

        <div className="login-box">
            <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/css/bootstrap.min.css" integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossOrigin="anonymous" />

            <Helmet>
                <script src="https://code.jquery.com/jquery-3.3.1.slim.min.js" integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/popper.js@1.14.7/dist/umd/popper.min.js" integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1" crossorigin="anonymous"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.3.1/dist/js/bootstrap.min.js" integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM" crossorigin="anonymous"></script>
            </Helmet>
            <ul class="d-flex justify-content-center  nav nav-pills nav-justified mb-3">
                <li class="active"><a data-toggle="tab" href="#login">Login</a></li>
                <li><a data-toggle="tab" href="#signup">SignUp</a></li>

            </ul>

            <div class="tab-content">
                <div id="login" class="tab-pane fade in active">
                    <h3>Login</h3>
                    <Login></Login>
                </div>
                <div id="signup" class="tab-pane fade">
                    <h3>SignUp</h3>
                    <Signup></Signup>
                </div>

            </div>
        </div>



    )
}