import { useRouter } from "next/router";
import React, { useEffect, useState } from "react";
import { RenderImageProps } from "react-photo-gallery";
import { PhotoPropsCustom } from "../types/PhotoPropsCustom";
import { GalleryProps } from "./Gallery";
import GalleryModalContent from "./GalleryModalContent";
import { ModalContext, ModalContextType } from "./Modal/ModalContext";

const GalleryImage = ({ photo, margin, photos, index,hidePreviewModal }: RenderImageProps<PhotoPropsCustom> & GalleryProps) => {
    let { handleModal } = React.useContext<ModalContextType>(ModalContext);
    const router = useRouter()
    const [originalUrl, setOriginalUrl] = useState<string>(router.asPath);
    
    useEffect(()=>{
        const newUrl = router.asPath
        if(newUrl.startsWith('/search')){
            setOriginalUrl(newUrl)
        }
    },[router.asPath])

    const onCancel = () => {
        router.push({}, originalUrl, {shallow: true})
    }
    const handleOnClick = () => {
        if (handleModal) {
            handleModal(
                <GalleryModalContent index={index} photos={photos} showCloseButton={true} showPreviewButton={true} hidePreviewModal={hidePreviewModal}/>,
                { 
                    style: { 
                        minWidth: '50%'
                    },
                    className: "gallery-modal",
                    footer: null,
                    closable: false,
                    centered: true,
                    // disable animation
                    transitionName:"none",
                    maskTransitionName:"none",
                    onCancel
                }
            )
        }
    }

    const selectImage = () => {
        if(hidePreviewModal && photo){
            const currentPicture = photo
            router.replace('/preview/' + currentPicture?.slug);
        }else{
            handleOnClick()
        }
    }

    return (
        <div
            style={{ margin, height: photo.height, width: photo.width }}

        >
            <div
                className="gallery-image-div"
                style={{
                    position: 'absolute',
                    height: photo.height,
                    width: photo.width
                }}
                onClick={selectImage}
            />
            <img
                className="gallery-image"
                {...photo as any}
                alt={photo.title}
                key={photo.id}
            />
        </div>
    )
}

export default GalleryImage;