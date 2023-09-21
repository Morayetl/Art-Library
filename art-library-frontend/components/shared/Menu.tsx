import { Menu as AntDMenu } from 'antd';
import { useRouter } from 'next/router'
import { COOKIE_NAME_AUTHORIZATION, COOKIE_NAME_USER_CREDENTIALS, SITE_NAME } from '../constants';
import { removeCookies, getCookie } from 'cookies-next';
import { useCallback, useEffect, useState } from 'react';
import Link from 'next/link';



const routes: Record<string, any> = {
  'home': '/',
  'about': '/about-us',
  'terms': 'terms-and-agreement'
}

type MenuProps = {
  iconTextColor?: string
}

const newItems: any = [
  { label: (<Link  href="/"><span className="menu-home" style={{ color: 'white', fontSize: '30px', fontWeight: '500' }}>{SITE_NAME}</span></Link>), key: 'home', id: 'custom-menu-home-icon' },
  /*{ label: 'About Us', key: 'about'},
  { label: 'Terms and Agreement', key: 'terms' }*/
];

const Menu = (props: MenuProps) => {
  const [items, setItem] = useState<Array<any>>(newItems);
  const router = useRouter()

  const getAuthCookie = useCallback(() => {
    return getCookie(COOKIE_NAME_AUTHORIZATION);
  }, [])

  const authCookie = getAuthCookie()
  const onClick = (e: any) => {
    if (e.key === 'logout') {
      const newItems = items;
      newItems.pop();

      setItem(newItems)
      removeCookies(COOKIE_NAME_USER_CREDENTIALS)
      removeCookies(COOKIE_NAME_AUTHORIZATION)
      if (router.route === '/') {
        router.reload()
      } else {
        router.push('/')
      }

      return;
    }
    if (e.key !== 'home') {
      router.push(routes[e.key])
    }
  }

  useEffect(() => {
    if (authCookie) {
      setItem([
        ...items,
        { label: 'Logout', key: 'logout' }
      ])
    }
  }, [authCookie])

  return (
    <AntDMenu onClick={onClick} items={items} mode="horizontal" style={{ height: '65px', background: 'none', borderBottom: 'none', color: 'white', width: '100%' }} />
  )
}

export default Menu;