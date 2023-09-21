import axiosInstance from "../axios"
import ICredentials from "../types/ICredentials"
import IUser from "../types/IUser"

const baseUrl = '/auth'
const authApi = {
    login: (userCredentials:ICredentials) => {
        return axiosInstance.post<IUser>(
            baseUrl +'/login',
            userCredentials,
            {
                params: {
                    "g-recaptcha-response": userCredentials.captcha
                }
            }
        )
    }
}

export default authApi;