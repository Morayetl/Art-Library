import { UploadFile } from "antd/lib/upload/interface";
import { ImageCustomProps } from "./PhotoPropsCustom";

export interface IMedia extends ImageCustomProps {
    file: {
        file: UploadFile,
        fileList: Array<UploadFile>
    }
}
