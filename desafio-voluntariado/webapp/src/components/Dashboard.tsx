import React from 'react';

interface DashboardProps {
  onLogout: () => void;
}

function Dashboard({ onLogout }: DashboardProps) {
  const userEmail = localStorage.getItem('userEmail');

  return (
    <div className="dashboard">
      <header>
        <h1>Dashboard</h1>
        <p>Bem-vindo, {userEmail}</p>
        <button onClick={onLogout}>Sair</button>
      </header>
      <main>
        <div className="dashboard-content">
          <h2>Seus Dados</h2>
          <p>Email: {userEmail}</p>
          <section>
            <h3>Resumo</h3>
          </section>
        </div>
      </main>
    </div>
  );
}

export default Dashboard;