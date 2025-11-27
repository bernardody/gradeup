import './MyAlerts.css';

export default function MyAlerts() {

  return (
    <div className="my-alerts">
      <h3 className="my-alerts__title">Enviar aviso para a turma</h3>
      <textarea
        placeholder="Escreva seu aviso..."
        className="my-alerts__textarea"
        rows={4}
      />
      <button className="my-alerts__button">
        Enviar
      </button>
    </div>
  );
}