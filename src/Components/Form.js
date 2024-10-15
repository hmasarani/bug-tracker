import React, { useState, useEffect } from 'react';
import { Form, FormGroup, Label, Input, Button } from 'reactstrap';

function FormToSubmit() {
  const [title, setTitle] = useState('');
  const [description, setDescription] = useState('');
  const [priority, setPriority] = useState('LOW');
  const [status, setStatus] = useState('OPEN');
  const [userId, setUserId] = useState('');
  const [users, setUsers] = useState([]);
  const [bugs, setBugs] = useState([]); // State for bugs

  useEffect(() => {
    const fetchUsers = async () => {
        try {
          const response = await fetch('/api/v1/users');
          if (!response.ok) {
            const errorMessage = await response.text();
            throw new Error(`Error fetching users: ${errorMessage}`);
          }
          const body = await response.json();
          console.log('Fetched users:', body); // Log the users
          setUsers(body);
        } catch (error) {
          console.error('Fetch error:', error.message);
        }
      };

    const fetchBugs = async () => {
      try {
        const response = await fetch('/api/v1/bugs');
        if (!response.ok) {
          const errorMessage = await response.text();
          throw new Error(`Error fetching bugs: ${errorMessage}`);
        }
        const body = await response.json();
        setBugs(body);
      } catch (error) {
        console.error('Fetch error:', error.message);
      }
    };

    fetchUsers();
    fetchBugs(); // Fetch bugs when the component loads
  }, []);

  const handleSubmitClick = async () => {
    try {
      console.log("User ID Selected:", userId);
      console.log("Users array:", JSON.stringify(users, null, 2));
  
      const selectedUser = users.find(user => user.id === Number(userId));
  
      console.log("Selected user:", selectedUser);
  
      if (!selectedUser) {
        throw new Error("Selected user not found");
      }
  
      const response = await fetch('/api/v1/bugs', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify({
          title,
          description,
          priority,
          status,
          user: {
            id: selectedUser.id,
            firstName: selectedUser.firstName,
            lastName: selectedUser.lastName,
            email: selectedUser.email,
            role: selectedUser.role,
          },
        }),
      });
  
      if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(`Error submitting bug: ${errorMessage}`);
      }
  
      alert('Bug submitted successfully');
      window.location.reload();
      const newBug = await response.json();
      console.log(newBug);
    } catch (error) {
      console.error('Submit error:', error.message);
    }
  };
  
  
  

  return (
    <Form>
      <FormGroup>
        <Label for="title">Title</Label>
        <Input
          id="title"
          name="title"
          placeholder="Enter title of bug"
          type="text"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
        />
      </FormGroup>
      <FormGroup>
        <Label for="description">Description</Label>
        <Input
          id="description"
          name="description"
          placeholder="Enter description of bug"
          type="text"
          value={description}
          onChange={(e) => setDescription(e.target.value)}
        />
      </FormGroup>
      <FormGroup>
        <Label for="priority">Priority</Label>
        <Input
          id="priority"
          name="priority"
          type="select"
          value={priority}
          onChange={(e) => setPriority(e.target.value)}
        >
          <option>LOW</option>
          <option>MEDIUM</option>
          <option>HIGH</option>
        </Input>
      </FormGroup>
      <FormGroup>
        <Label for="status">Status</Label>
        <Input
          id="status"
          name="status"
          type="select"
          value={status}
          onChange={(e) => setStatus(e.target.value)}
        >
          <option>OPEN</option>
          <option>IN_PROGRESS</option>
          <option>CLOSED</option>
          <option>RESOLVED</option>
        </Input>
      </FormGroup>
      <FormGroup>
  <Label for="user">User</Label>
  <Input
  id="user"
  name="user"
  type="select"
  value={userId}
  onChange={(e) => setUserId(e.target.value)}
>
  {Array.isArray(users) && users.map((user) => (
    <option key={user.id} value={user.id}>
      {user.firstName} {user.lastName}
    </option>
  ))}
</Input>
</FormGroup>

      <Button onClick={handleSubmitClick}>Submit</Button>
    </Form>
  );
}

export default FormToSubmit;
