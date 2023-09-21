import * as ReactGallery from 'react-photo-gallery';

/**
 * Properties are used to ext
 */

export interface ImageCustomProps {
    title?: string,
    description?: string,
    size?: number,
    tags?: Array<string>,
    type?: string,
    slug?: string,
    id?: string,
    published?: boolean,
    originalImageHeight?: number,
    originalImageWidth?: number,
    views?: number,
    created?: string
}

export type PhotoPropsCustom = ReactGallery.PhotoProps<ImageCustomProps>;