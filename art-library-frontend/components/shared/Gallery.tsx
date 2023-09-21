import { Button, Result, Row, Spin } from "antd";
import React, { useCallback, useEffect, useRef, useState } from "react";
import * as ReactGallery from 'react-photo-gallery';
import { PhotoPropsCustom } from "../types/PhotoPropsCustom";
import GalleryImage from "./GalleryImage";



export type GalleryProps = {
  photos: Array<PhotoPropsCustom>,
  onPageLoad?: (page: number) => void,
  resultCount?: number,
  currentPage?: number,
  loading?: boolean,
  resultNotFound?: boolean,
  serverError?: boolean,
  pageSize?: number,
  hidePreviewModal?: boolean
}

const Gallery = (props: GalleryProps) => {
  const [isInBottom, setIsInBottom] = useState<Boolean>(false);
  const [ref, setRef] = useState<HTMLDivElement>();

  const onRefUpdate = useCallback((node: HTMLDivElement | null) => {
    if(node){
      setRef(node);
    }
  },[setRef]);

  const onScroll = () => {
    if(!props.resultCount){
      return;
    }

    const pageSize = props.resultCount / (props.pageSize || 0);
    const maxPage = (pageSize < 1 ? 0 : Math.ceil(pageSize)) -1;

    if (ref && !isInBottom && props?.currentPage !== undefined && props?.currentPage < maxPage) {

      const boundingRect = ref.getBoundingClientRect();
      if (boundingRect.bottom <= window.innerHeight) {

        setIsInBottom(true);
        if(props.onPageLoad){
          props.onPageLoad(props.currentPage +1)
          setTimeout(() => {
            setIsInBottom(false);
          },100)
        }
      }
    }
  };

  React.useEffect(() => {
    window.addEventListener('scroll', onScroll, {
      passive: true
    });

    return () => {
      window.removeEventListener('scroll', onScroll);
    };
  }, [onScroll]);

  if (typeof document === 'undefined') {
    React.useLayoutEffect = React.useEffect;
  }

  const imageRenderer = useCallback(
    (props: ReactGallery.RenderImageProps<any> & GalleryProps) => (
      <GalleryImage
        {...props}
        key={props.index}
        margin={'2px'}
        photos={props.photos}
      />
    ),
    []
  );

  if(props.resultNotFound && props.photos.length === 0){
    return(
      <Result
      status="404"
      title="Result not found"
    />
    )
  }

  if(props.serverError && !props.loading){
    return(
      <Result
      status="500"
      title="Whoops.. Something went wrong, try reloading the page"
    />
    )
  }
  
  if(props.loading && props.photos.length === 0){
    return(
      <Row style={{display: 'flex', height: 'inherit', margin: '12% 0 12%'}}>
        <Spin style={{ margin: 'auto'}} tip="Loading..."></Spin>
      </Row>
    )
  }

  return (
    <div ref={onRefUpdate}>
        <ReactGallery.default targetRowHeight={400} photos={props.photos} renderImage={(p) => imageRenderer({ ...p, ...props })} />
    </div>
  )
}

export default Gallery;