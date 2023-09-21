import React from "react";
import ReactDOM from "react-dom";
import { ModalContext, ModalContextType } from "./ModalContext";
import {Modal as AntdModal} from 'antd'

const Modal = () => {
  let { modalContent, handleModal, modal, modalProps } = React.useContext<ModalContextType>(ModalContext);
  const closeModal = () => {
    if(modalProps?.onCancel){
      modalProps.onCancel(undefined as any)
    }
    if(handleModal){
      handleModal();
    }
  }
  if (modal) {
    return ReactDOM.createPortal(
        <AntdModal  {...modalProps} visible={modal} onOk={handleModal as any} onCancel={(closeModal as any)}>
        {modalContent}
      </AntdModal>,
      document.querySelector("#modal-root")!
    );
  } else return null;
};

export default Modal;
