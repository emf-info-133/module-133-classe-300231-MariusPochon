const form = document.getElementById('loginForm');
const errorMsg = document.getElementById('errorMsg');

form.addEventListener('submit', async (e) => {
  e.preventDefault();

  const username = document.getElementById('username').value;
  const password = document.getElementById('password').value;
 
  try {
    console.log(JSON.stringify({ username, password }));
    const response = await fetch('https://133.khalils.emf-informatique.ch/login', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({ username, password })
    });

    const text = await response.text(); 
    if (response.ok) {
      if(text === 'user'){
        alert(text); 
        window.location.href = './html/AllBooks.html';
      } else {
        errorMsg.textContent = "Accès refusé : vous n'êtes pas un utilisateur. Si vous avez un compte administateur, connectez-vous à la plateforme dédiés";
      }
     
    }else {
      errorMsg.textContent = text || 'Erreur de connexion';
    }

  } catch (err) {
    console.error(err);
    errorMsg.textContent = 'Erreur serveur';
  }
});
