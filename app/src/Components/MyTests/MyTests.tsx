import './MyTests.css';

export default function MyTests() {
  return (
    <div className="my-tests">
      <h3 className="my-tests__title">Minhas provas</h3>
      <div className="my-tests__buttons">
        <button className="my-tests__button">
          Publicar prova
        </button>
        <button className="my-tests__button">
          Ver provas
        </button>
      </div>
    </div>
  );
}