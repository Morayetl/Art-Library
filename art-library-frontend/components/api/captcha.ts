import axiosInstance from "../axios"
import ICaptchaSiteKey from "../types/ICaptchaSiteKey"

const baseUrl = '/captcha'
const captchaApi = {
    getRecaptchaKey: () => {
        return axiosInstance.get<ICaptchaSiteKey>(
            baseUrl
        )
    }
}

export default captchaApi;