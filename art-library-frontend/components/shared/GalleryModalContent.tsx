import { CloseOutlined, CopyOutlined, DownloadOutlined, EyeOutlined, LeftOutlined, RightOutlined, } from "@ant-design/icons"
import { Button, Checkbox, Col, Divider, Image, Input, message, Modal, Row, Spin, Tag, Typography } from "antd"
import React, { useEffect, useRef, useState } from "react"
import { PhotoPropsCustom } from "../types/PhotoPropsCustom"
import { ModalContext, ModalContextType } from "./Modal/ModalContext"
import Ad from "./Ad"
import download from 'downloadjs';
import { FRONTEND_URL } from "../constants"
import mediaApi from "../api/media"
import { useRouter } from "next/router"
import captchaApi from "../api/captcha"
import ReCAPTCHA from "react-google-recaptcha";
import { CopyToClipboard } from 'react-copy-to-clipboard';
import searchApi from "../api/search"
import Gallery from "./Gallery"
//@ts-ignore
import {
    FacebookShareButton,
    FacebookIcon,
    TwitterShareButton,
    TwitterIcon,
    RedditIcon,
    RedditShareButton,
    LinkedinIcon,
    LinkedinShareButton,
    WhatsappShareButton,
    WhatsappIcon
} from 'next-share';
import moment from "moment"
const { confirm } = Modal;

type GalleryModalContentType = {
    index: number,
    photos: Array<PhotoPropsCustom>,
    showCloseButton?: boolean
    showPreviewButton?: boolean,
    showGallery?: boolean,
    id?: string,
    hidePreviewModal?: boolean
}

const galleryAdId = 'gallery-ad';

const { Title, Paragraph, Text } = Typography;
const GalleryModalContent = ({ index, photos, showCloseButton, showPreviewButton, showGallery, id, hidePreviewModal }: GalleryModalContentType) => {
    const grecaptchaObject = (window as any).grecaptcha;

    const router = useRouter()
    const [pageURL, setPageURL] = useState('');
    const [originalUrl] = useState<string>(router.asPath);

    const { handleModal, modal } = React.useContext<ModalContextType>(ModalContext);
    const [currentIndex, setCurrentIndex] = useState<number>(photos.length > 1 ? index : 0)
    const [currentPicture, setCurrentPicture] = useState<PhotoPropsCustom>()
    const [galleryPhotos, setGalleryPhotos] = useState<Array<PhotoPropsCustom>>([])
    const [captcha, setCaptcha] = useState<string | null>(null);
    const [loading, setLoading] = useState<boolean>(false);
    const [isDownloadModalVisible, setIsDownloadModalVisible] = useState<boolean>(false);
    const [agreementAccepted, setAgreementAccepted] = useState<boolean>(false);
    const [downloadButtonLoading, setDownloadButtonLoading] = useState<boolean>(false);
    const [captchaKey, setCaptchaKey] = useState('');
    const captchaRef = useRef<ReCAPTCHA>(null);
    const [recaptchaCount, setRecaptchaCount] = useState<number>(0);

    useEffect(() => {
        captchaApi
            .getRecaptchaKey()
            .then((res) => setCaptchaKey(res.data.key));
    }, [])

    // set page url for social media share
    useEffect(() => {
        if (typeof window !== 'undefined') {
            setPageURL(window.location.href);
        }
    }, [window?.location?.href])

    useEffect(() => {
        if (currentPicture?.slug && modal) {
            router.push({}, '/preview/' + currentPicture?.slug, { shallow: true })
        }
    }, [currentPicture?.slug])

    useEffect(() => {
        if (showGallery && id && photos) {
            getMedia(id, 0, true);
            //@ts-ignore
            document.getElementById(galleryAdId)?.getElementsByTagName('iframe')[0]?.src += '';
        }
    }, [id, currentPicture?.id])

    const changePicture = (i: number) => {
        setCurrentIndex(i);
        const curPic = photos[i];
        getMedia(curPic.id, i)
        // update iframe when page changed
        //@ts-ignore
        document.getElementById(galleryAdId)?.getElementsByTagName('iframe')[0]?.src += '';
    }

    const getMedia = (id: string | undefined, index: number, withGalleryImages = false) => {
        if (loading) {
            return;
        }

        setLoading(true);
        if (id) {
            mediaApi.getMeta(id).then((res) => {
                setCurrentPicture(res.data)
                if (withGalleryImages) {
                    const search = res.data.title || '';
                    searchApi.search(search, 0).then((res) => {
                        setGalleryPhotos(res.data.results)
                    })
                }
            }).finally(() => {
                setTimeout(() => setLoading(false), 250)
            })
        } else {
            // set Current picture when local environment and connection doesnt work
            setCurrentPicture(photos[index])
            setLoading(false);
        }
    }

    const closeModal = (withoutChangingUrl?: boolean) => {
        if (!withoutChangingUrl) {
            router.push({}, originalUrl, { shallow: true })
        }

        if (handleModal) {
            handleModal();
        }
    }

    useEffect(() => {
        setLoading(true);
        const curPic = photos[index];
        if (curPic?.id) {
            mediaApi.getMeta(curPic.id).then((res) => {
                setCurrentPicture(res.data)
            }).finally(() => {
                setLoading(false);
            })
        } else {
            setCurrentPicture(curPic)
            setLoading(false);
        }
    }, [id])

    const onDownload = () => {
        if (!currentPicture || !captcha) {
            return;
        }
        const extension = currentPicture?.type?.split('/')?.[1];
        setDownloadButtonLoading(true);
        mediaApi
            .download(currentPicture.id, captcha).then((res) => download(res.data, `${currentPicture?.slug}.${extension}`, currentPicture?.type))
            .then(() => {
                setIsDownloadModalVisible(false)
                captchaRef.current?.reset();
                setAgreementAccepted(false)
            }).finally(() => {
                setCaptcha('')
                setDownloadButtonLoading(false)
            })
    }

    const viewMedia = (media: PhotoPropsCustom | undefined) => {
        if (media && showPreviewButton) {
            router.push({ pathname: '/preview/' + currentPicture?.slug }, '/preview/' + currentPicture?.slug)
            setTimeout(() => closeModal(true), showGallery ? 300 : 0);
        }
    }

    const getPreviewUrl = (photo: PhotoPropsCustom | undefined) => FRONTEND_URL + '/preview/' + photo?.id;

    const onOk = () => {
        onDownload()
    }

    useEffect(() => {
        if (isDownloadModalVisible && grecaptchaObject && captchaKey) {
            setTimeout(() => {
                grecaptchaObject.render(document.getElementById('captcha-component'), {
                    'sitekey': captchaKey,
                    'callback': setCaptcha,
                    'theme': 'light'
                });
            }, 1000)
        }
    }, [isDownloadModalVisible, grecaptchaObject])

    const onDownloadModalOpen = () => {
        setIsDownloadModalVisible(true)
        setRecaptchaCount(recaptchaCount + 1);
        captchaRef.current?.render();
    }

    const onDownloadModalClose = () => {
        setIsDownloadModalVisible(false)
        setAgreementAccepted(false)
        captchaRef.current?.render();
    }

    const onCopy = () => {
        message.info('Link copied to clipboard');
    }

    return (
        <div>
            <Row style={{ width: '100%' }} justify="space-between">
                <Col span={20}>
                    <h1 className="modal-title" style={{ marginLeft: '10px' }}>
                        {currentPicture?.title}
                    </h1>
                </Col>
                <Col span={4} style={{ display: 'flex' }}>

                    {
                        showCloseButton && (
                            <Button style={{ marginLeft: 'auto', marginRight: '0' }} onClick={() => closeModal()} size="large" type="link" shape="circle" icon={<CloseOutlined />} />
                        )
                    }
                </Col>
            </Row>
            <Divider style={{ marginTop: '10px', marginBottom: '15px' }} dashed />
            {
                (loading && showGallery || !currentPicture) ? (
                    <Row style={{ display: 'flex', width: '100%', height: "50vh" }}>
                        <Spin style={{ margin: 'auto' }} tip="Loading..."></Spin>
                    </Row>
                ) :
                    (
                        <>
                            <div style={{ width: '100%', height: '10vh' }}>
                                <Ad id={galleryAdId} height={90} className="image-preview-ad" />
                            </div>
                            <div style={{ display: 'flex', width: '100%', height: "50vh" }}>
                                <Image
                                    key={currentPicture?.src}
                                    src={currentPicture?.src}
                                    height="inherit"
                                    width="inherit"
                                    style={{
                                        margin: 'auto',
                                        objectFit: 'contain',
                                        width: 'inherit',
                                        height: 'inherit',
                                        zIndex: -1
                                    }} />
                            </div>
                            <Row style={{ width: '100%', justifyContent: 'center', marginTop: '10px' }}>
                                <Col span={12}>
                                    <Row style={{ width: '100%', justifyContent: 'end', marginRight: '20px' }}>
                                        {
                                            photos && currentIndex > 0 && (
                                                <div style={{ marginRight: '20px' }}><Button className="gallery-navigate-button button-shadow" onClick={() => changePicture(currentIndex - 1)} size="large" type="default" shape="circle" icon={<LeftOutlined style={{ fontSize: "15px" }} />} /></div>
                                            )
                                        }
                                    </Row>
                                </Col>

                                <Col span={12}>
                                    <Row style={{ width: '100%', justifyContent: 'start', marginLeft: '20px' }}>
                                        {
                                            photos && currentIndex < (photos.length - 1) && (
                                                <Button className="gallery-navigate-button button-shadow" onClick={() => changePicture(currentIndex + 1)} size="large" type="default" shape="circle" icon={<RightOutlined style={{ fontSize: "15px" }} />} />
                                            )
                                        }
                                    </Row>
                                </Col>
                            </Row>
                            <Row style={{ width: '100%' }}>
                                <Row style={{ width: '100%', display: 'flex', marginTop: '20px' }}>
                                    {
                                        currentPicture?.description && (
                                            <Row style={{ width: '100%', display: 'flex' }}>
                                                <Paragraph style={{ margin: 'auto', marginBottom: '20px', maxWidth: '600px', textAlign: 'center' }}>
                                                    {currentPicture.description}
                                                </Paragraph>
                                            </Row>
                                        )
                                    }

                                    {
                                        currentPicture?.created && (
                                            <Row style={{ width: '100%', display: 'flex' }}>
                                                <Text style={{ fontWeight: 600, color: 'grey', margin: 'auto', marginBottom: '20px', maxWidth: '600px', textAlign: 'center' }}>
                                                    Published {moment(currentPicture.created).format('DD.MM.YYYY')}
                                                </Text>
                                            </Row>
                                        )
                                    }

                                    <Row style={{ width: '100%', display: 'flex' }} justify="center">
                                        {
                                            currentPicture?.tags?.map((tag, index) => <Tag key={tag + '-' + index} style={{ padding: '10px', marginTop: '10px' }} color="default">{tag}</Tag>)
                                        }
                                    </Row>

                                </Row>
                                <Row style={{ width: '100%', display: 'flex', marginTop: '20px' }} justify="center">
                                    <FacebookShareButton
                                        style={{ marginRight: '10px' }}
                                        url={pageURL}
                                        hashtag={'#nextshare'}
                                    >
                                        <FacebookIcon size={40} round />
                                    </FacebookShareButton>
                                    <TwitterShareButton
                                        style={{ marginRight: '10px' }}
                                        url={pageURL}
                                    >
                                        <TwitterIcon size={40} round />
                                    </TwitterShareButton>
                                    <WhatsappShareButton
                                        style={{ marginRight: '10px' }}
                                        url={pageURL}
                                    >
                                        <WhatsappIcon size={40} round />
                                    </WhatsappShareButton>
                                    <RedditShareButton
                                        style={{ marginRight: '10px' }}
                                        url={pageURL}
                                    >
                                        <RedditIcon size={40} round />
                                    </RedditShareButton>
                                    <LinkedinShareButton
                                        url={pageURL}
                                    >
                                        <LinkedinIcon size={40} round />
                                    </LinkedinShareButton>
                                </Row>
                                <Row style={{ width: '100%', display: 'flex', marginTop: '20px' }} justify="center">

                                    <Button onClick={onDownloadModalOpen} className="download-button button-shadow hover-button" style={{ background: 'green', color: "white", marginRight: '5px', marginLeft: '5px', marginTop: '10px' }} shape="default" icon={<DownloadOutlined />} size="large">
                                        Download {` (${currentPicture.originalImageHeight} x ${currentPicture.originalImageWidth}) pixels`}
                                    </Button>
                                    {
                                        showPreviewButton && (
                                            <Button onClick={() => viewMedia(currentPicture)} className="download-button button-shadow hover-button" type="primary" style={{ marginRight: '5px', marginLeft: '5px', marginTop: '10px' }} shape="default" icon={<EyeOutlined />} size="large">
                                                View image
                                            </Button>
                                        )
                                    }

                                </Row>
                                <Modal destroyOnClose={true} title="Download file" okButtonProps={{ disabled: !captcha || !agreementAccepted, loading: downloadButtonLoading }} visible={isDownloadModalVisible} onOk={onOk} onCancel={onDownloadModalClose}>
                                    <Row className="custom-download-checkbox-row" style={{ width: '100%' }}>
                                        <Checkbox checked={agreementAccepted} className={`custom-download-checkbox ${agreementAccepted ? 'selected' : ''}`} onChange={(e) => setAgreementAccepted(!agreementAccepted)}> I Agree to add credit for the artist</Checkbox>
                                    </Row>
                                    {
                                        agreementAccepted && (
                                            <>
                                                <Row style={{ width: '100%' }}>
                                                    <Text>
                                                        Copy this link to your project, description or website.
                                                    </Text>
                                                </Row>
                                                <Row className="custom-download-copy-link" style={{ width: '100%', marginBottom: '15px' }}>
                                                    <Col style={{ flex: 1 }}>
                                                        <Input size="large" value={getPreviewUrl(currentPicture)} />
                                                    </Col>
                                                    <Col>
                                                        <CopyToClipboard text={getPreviewUrl(currentPicture)} onCopy={onCopy}>
                                                            <Button size="large" type="link" icon={<CopyOutlined />} />
                                                        </CopyToClipboard>

                                                    </Col>
                                                </Row>

                                            </>

                                        )
                                    }
                                    <Row style={{ width: '100%' }}>
                                        <Col style={{ marginLeft: '0' }}>
                                            {
                                                (captchaKey) && (
                                                    <>
                                                        <div id="captcha-component"></div>
                                                    </>
                                                )
                                            }
                                        </Col>
                                    </Row>
                                </Modal>
                            </Row>
                        </>
                    )
            }

            {
                (showGallery && galleryPhotos) && (
                    <>
                        <Divider style={{ marginTop: '20px', marginBottom: '20px' }} dashed />
                        <Row style={{ display: 'flex', width: '100%', justifyContent: 'center' }}>
                            <h2 style={{ marginLeft: '10px' }}>
                                More pictures
                            </h2>
                        </Row>
                        <Gallery
                            photos={galleryPhotos}
                            loading={loading}
                            hidePreviewModal={hidePreviewModal}
                        />
                    </>
                )
            }

        </div>
    )
}

export default GalleryModalContent;