import React, { Component } from 'react';
import AppNav from './AppNav';

export default function Home(){
    return (
        <div>
         <AppNav/>
         <h2 style={{display: 'flex',  justifyContent:'center', alignItems:'center', height: '100vh'}}>
           Welcome to easy Bug Tracker Application! 
           </h2>
          </div>
        );
}
