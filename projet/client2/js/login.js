const form = document.getElementById('loginForm');
const errorMsg = document.getElementById('errorMsg');

form.addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
 
  try {
    console.log(JSON.stringify({ username, password }));
    const response = await fetch('http://localhost:8080/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    const text = await response.text(); 
    if (response.ok) {
      alert(text); 
      window.location.href = '../../client1/index.html';
    } else {
      errorMsg.textContent = text || 'Erreur de connexion';
    }

  } catch (err) {
    console.error(err);
    errorMsg.textContent = 'Erreur serveur';
  }
});
