import './MyWorks.css';

export default function MyWorks() {
  return (
    <div className="my-works">
      <h3 className="my-works__title">Meus trabalhos</h3>
      <div className="my-works__buttons">
        <button className="my-works__button">
          Publicar trabalho
        </button>
        <button className="my-works__button">
          Ver trabalhos
        </button>
      </div>
    </div>
  );
}