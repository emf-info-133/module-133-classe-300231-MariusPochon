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
        'Content-Type': 'application/json' // C’est ce qui indique le format
      },
      body: JSON.stringify({ username, password })
    });

    const text = await response.text(); // <-- Ton API retourne juste un texte simple
    if (response.ok) {
      alert(text); // "Connecté avec succès"
      window.location.href = './html/oui.html';// Tu peux rediriger vers le dashboard ou autre ici
    } else {
      errorMsg.textContent = text || 'Erreur de connexion';
    }

  } catch (err) {
    console.error(err);
    errorMsg.textContent = 'Erreur serveur';
  }
});
