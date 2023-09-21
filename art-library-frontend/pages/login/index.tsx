import { NextPage } from 'next'
import { Button, Form, Input, Layout, Row } from 'antd';
import ICredentials from '../../components/types/ICredentials';
import { useRouter } from 'next/router';
import authApi from '../../components/api/auth';
import { setCookies } from 'cookies-next';
import { COOKIE_NAME_AUTHORIZATION, COOKIE_NAME_USER_CREDENTIALS } from '../../components/constants';
import { useEffect, useState } from 'react';
import captchaApi from '../../components/api/captcha';

const Login: NextPage = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const router = useRouter();
  const [captchaKey, setCaptchaKey] = useState('');
  const [captcha, setCaptcha] = useState<string | null>(null);

  const onLogin = (values: ICredentials) => {
    if (!captcha) {
      return;
    }

    setLoading(true)
    authApi
      .login({
        ...values,
        captcha
      })
      .then((res) => {
        setCookies(COOKIE_NAME_USER_CREDENTIALS, JSON.stringify(res.data))
        setCookies(COOKIE_NAME_AUTHORIZATION, res.data.jwt)

        router.push('/user/media')
      })
      .finally(()=>{
        setLoading(false)
      })
  }

  useEffect(() => {
    captchaApi
      .getRecaptchaKey()
      .then((res) => setCaptchaKey(res.data.key));
  }, [])


  useEffect(() => {
    if (typeof window !== 'undefined' && captchaKey) {
      setTimeout(() => {
        (window as any).grecaptcha.render(document.getElementById('captcha-component-login'), {
          'sitekey': captchaKey,
          'callback': setCaptcha,
          'theme': 'light'
        });
      }, 1000)
    }
  }, [captchaKey])

  return (
    <Layout>
      <Row style={{ minHeight: '100vh' }}>
        <div style={{ margin: 'auto' }}>
          <div style={{ width: '500px', border: '1px solid gray', padding: '20px', borderRadius: '2', background: 'white' }}>
            <Row style={{ width: '100%' }}>
            </Row>
            <Form onFinish={onLogin} layout="vertical" autoComplete="off" size="large">
              <Form.Item name="username" label="Username" required >
                <Input />
              </Form.Item>
              <Form.Item name="password" label="Password" required>
                <Input type="password" />
              </Form.Item>
              {
                (captchaKey) && (
                  <Form.Item>
                    <div id="captcha-component-login"></div>
                  </Form.Item>
                )
              }
              <Form.Item>
                <Button type="primary" htmlType="submit" style={{ width: '100%' }} loading={loading}>
                  Login
                </Button>
              </Form.Item>
            </Form>
          </div>
        </div>
      </Row>

    </Layout>
  )
}

export default Login