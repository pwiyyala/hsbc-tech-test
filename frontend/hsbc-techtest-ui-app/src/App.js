// App.js
import React, { useState } from 'react';
import axios from 'axios';

const App = () => {
  const [letter, setLetter] = useState('');
  const [response, setResponse] = useState('');

  const handleInputChange = (e) => {
    setLetter(e.target.value);
  };

  const handleSubmit = async () => {
    try {
      const res = await axios.get('http://localhost:8080/api/v1/cities/'+letter);
      setResponse(res.data.length > 0 ? res.data.length : 'No cities found');
    } catch (error) {
      console.error('Error calling API:', error);
      setResponse('Failed to fetch data.');
    }
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Enter a Letter</h1>
      <input
        type="text"
        value={letter}
        onChange={handleInputChange}
        maxLength={10}
        placeholder="Enter a letter"
      />
      <button onClick={handleSubmit}>Submit</button>
      <p>Response: {response}</p>
    </div>
  );
};

export default App;