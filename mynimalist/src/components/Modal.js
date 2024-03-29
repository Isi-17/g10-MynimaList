import React, {useRef} from 'react'
import "../styles/Modal.css";

function Modal({children, showModal, setShowModal}){ 
    // Componente usado para hacer todos las ventanas flotantes, se cierra cuando pulsamos fuera de ellas
    const modalRef = useRef()

    const closeModal = (e) => {
        if(e.target === modalRef.current) {
            setShowModal(false)
        }
    }

    return (
        showModal &&
        <div className="Modal" ref={modalRef} onClick={closeModal}>
            <div className="container">
                {children}
            </div>
        </div>
    )
}

export default Modal