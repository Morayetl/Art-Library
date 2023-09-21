import React from "react";
import useModal from "./useModal";
import Modal from "./Modal";
import { ModalProps } from "antd";

export type ModalContextType = {
  modal: boolean,
  handleModal: ((content?: JSX.Element, modalProps?: ModalProps) => void) | null,
  modalContent: JSX.Element | null,
  modalProps: ModalProps
}
let defaultData: ModalContextType = {
  modal: false,
  handleModal: null,
  modalContent: null,
  modalProps: {}
}
let ModalContext: React.Context<any>;
let { Provider } = (ModalContext = React.createContext<ModalContextType>(defaultData));

type ModalProviderType = {
  children: JSX.Element
}
let ModalProvider = ({ children }: ModalProviderType) => {
  let { modal, handleModal, modalContent, modalProps } = useModal();
  return (
    <Provider value={{ modal, handleModal, modalContent, modalProps }}>
      <Modal />
      {children}
    </Provider>
  );
};

export { ModalContext, ModalProvider };
