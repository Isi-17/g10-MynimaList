import React, { useState, useContext } from "react";
import axios from 'axios';
import qs from 'qs';
import '../styles/Login.css';
import LogoWriting from "../components/LogoWriting";
import { useNavigate } from 'react-router-dom';
import { TaskContext } from '../context';
function Login() {

  const { setTokenA, setSelectedList } = useContext(TaskContext)

  setSelectedList(-1) // REVISAR

  const [username, setUsername] = useState('');
  const [password, setPassword] = useState('');

  let navigate = useNavigate();
  const handleClickSignUp = () => {
    navigate('/registro');
  };

  const handleClickSignIn = () => {
    var qs = require('qs');
    let body = { username: username, password: password }
    console.log(body);
    axios.post('http://localhost:8080/login', qs.stringify(body), 
    {
      headers: { 'content-type': 'application/x-www-form-urlencoded' }
    }).then( response => {
      setTokenA(response.data.access_token)  
      navigate('/main')
    }).catch( e => {
        console.log(e.response)
    })

  };
  const handleClickAboutUs = () => {
    navigate('/sobre-nosotros')
    }

  return (
    <div className="login-container">
      <LogoWriting />
      <form>
        <div className="user-container">
          <input
            type="text"
            value={username}
            onChange = { (e) => {
              setUsername(e.target.value);
              console.log(username);
            }}
          />
          <label>Usuario</label>
        </div>
        <div className="user-container">
          <input
            type="password"
            value={password}
            onChange = { (e) => {
              setPassword(e.target.value);
              console.log(password);
            }}
          />
          <label>Contraseña</label>
        </div>
        <button
          type="button"
          className="sign-in"
          onClick={handleClickSignIn}
        >
          Iniciar sesion
        </button>
        <button
          type="button"
          className="sign-up"
          onClick={handleClickSignUp}
        >
          Registrarse
        </button>
      </form>
      <button
          type="button"
          className="about-us"
          onClick={handleClickAboutUs}
        >
          Sobre Nosotros
        </button>
    </div>
  );
}

export default Login;
