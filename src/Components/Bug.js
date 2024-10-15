import React, { useState, useEffect } from 'react';
import AppNav from './AppNav'
import Form from './Form'
import '../App.css';  // Make sure you have a CSS file for styling
import TableBugView from './TableBugView'


export default function Bug() {
    // Define useState for isLoading and bugs
    const [isLoading, setIsLoading] = useState(true);
    const [bugs, setBugs] = useState([]); // Corrected useState for bugs

    useEffect(() => {
        const fetchBugs = async () => {
            try {
                const response = await fetch("/api/v1/bugs");
                if (!response.ok) {
                    throw new Error('Error fetching data');
                }
                const body = await response.json(); // Await the JSON response
                setBugs(body);
                setIsLoading(false);
            } catch (error) {
                console.error('fetch error', error);
                setIsLoading(false);
            }
        };
        fetchBugs();
    }, []);

    // Rendering
    if (isLoading) {
        return (
            <div>
                <h3>Bugs loading, please wait....</h3>
            </div>
        );
    } else {
        return (
            <div>
                <AppNav />
                <div className="form-container">
                    <Form />
                </div>
                <TableBugView className="table-container" />
               
            </div>
        );
    }
}