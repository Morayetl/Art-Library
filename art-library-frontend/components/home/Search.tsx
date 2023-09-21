import React, { useEffect, useRef, useState } from "react";
import { AutoComplete, Input, InputRef, Row, Select } from "antd";
import { DefaultOptionType } from "antd/lib/cascader";
import searchApi from "../api/search";
import { useRouter } from 'next/router'

const { Option } = AutoComplete;

type Props = {
  marginBottom?: string;
  marginTop?: string;
  redirectToSearchPage?: boolean,
  onSearch?: (query: string, page: number) => void,
  defaultValue?: string,
  loading?: boolean
}
const Search: React.FC<Props> = ({ marginTop, marginBottom, redirectToSearchPage, onSearch, defaultValue, loading }: Props) => {
  const router = useRouter()
  const [result, setResult] = useState<DefaultOptionType[]>([]);
  const [value, setValue] = useState<string>('');
  const [searchDefaultValue, setSearchDefaultValue] = useState<string | undefined>(defaultValue);
  let inputRef = useRef<InputRef | null>(null);

  const handleSearch = (value: string) => {
    let res: string[] = [];
    let opt: DefaultOptionType[] = [];
    // TODO: Implement autocomplete
    /*if (!value || value.indexOf('@') >= 0) {
      res = [];
    } else {
      res = ['gmail.com', '163.com', 'qq.com'].map(domain => `${value}@${domain}`);
      opt = res.map(v => (
        {
          value: v,
          label: (
            <Row style={{ width: '100%', height: '50px' }}>
              <span style={{ marginTop: 'auto', marginBottom: 'auto' }}>
                {v}
              </span>

            </Row>
          )
        }
      ))
    }

    setResult(opt);*/
  };

  useEffect(() => {
    setSearchDefaultValue(defaultValue)
  }, [defaultValue, setSearchDefaultValue])

  useEffect(() => {
    if(defaultValue){
      setValue(defaultValue);
    }
  }, [defaultValue])

  const search = () => {
    if (redirectToSearchPage) {
      router.push('/search?q=' + value)
      return;
    }

    if (onSearch) {
      onSearch(value, 0);
      router.replace('/search?q=' + value)
    }
  }

  return (
    <AutoComplete
      value={value}
      className="antd-custom-search"
      style={{ width: '100%', marginTop: marginTop || '10px', marginBottom: marginBottom || '20px' }}
      onSearch={handleSearch}
      onChange={(e:string) => setValue(e)}
      onSelect={(e:string) => setValue(e)}
      options={result}>

      <Input.Search ref={inputRef} size="large" placeholder="Search for something.." onSearch={search} loading={loading} />
    </AutoComplete>
  );
};

export default Search;
