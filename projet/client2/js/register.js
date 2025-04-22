const form = document.getElementById('registerForm');
const errorMsg = document.getElementById('errorMsg');

form.addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
  const confirmPassword = document.getElementById('confirmPassword').value;
  const isAdmin = document.getElementById('isAdmin').checked;
  const role = isAdmin ? "admin" : "user"; 

  if (password !== confirmPassword) {
    errorMsg.textContent = 'Les mots de passe ne correspondent pas';
    return;
  }
 
  try {
    console.log(JSON.stringify({ username, password, role }));
    const response = await fetch('http://localhost:8080/register', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password, role })
    });

    const text = await response.text(); 
    if (response.ok) {
      alert(text); 
      window.location.href = '../index.html';
    } else {
      errorMsg.textContent = text || 'Erreur de connexion';
    }
  } catch (err) {
    console.error('Erreur:', err);
    errorMsg.textContent = 'Erreur serveur';
  }
});