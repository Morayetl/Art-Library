import { ModalProps } from "antd";
import React from "react";

export default () => {
  let [modal, setModal] = React.useState(false);
  let [modalProps, setModalProps] = React.useState<ModalProps>({});
  let [modalContent, setModalContent] = React.useState<JSX.Element | null >(null);

  let handleModal = (content?: JSX.Element, modalProps?: ModalProps) => {
    setModal(!modal);
    if (content) {
      setModalContent(content);
    }

    if(modalProps){
        setModalProps(modalProps);
    }
  };

  return { modal, handleModal, modalContent, modalProps };
};
