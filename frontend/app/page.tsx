"use client";
import {
  Select,
  SelectContent,
  SelectGroup,
  SelectItem,
  SelectTrigger,
  SelectValue,
  SelectLabel,
} from "@/components/ui/select";
import { Input } from "@/components/ui/input";

import { use, useEffect, useState } from "react";

type ConvertResponse = {
  from: string;
  to: string;
  amount: number;
  rate: number;
  result: number;
};

async function callConvertApi(from: string, to: string, amount: string) {
  const qs = new URLSearchParams({
    from,
    to,
    amount,
  });
  const res = await fetch(
    `http://localhost:8080/api/conversion?${qs.toString()}`,
    {
      method: "GET",
    },
  );
  return (await res.json()) as ConvertResponse;
}

export default function Home() {
  const [curriencies, setCurrencies] = useState<string[]>([]);
  const [error, setError] = useState("");
  const [data, setData] = useState<ConvertResponse>();

  const [userInput, setUserInput] = useState({
    from: "SEK",
    to: "USD",
    amount: "",
  });


  async function handleConvert() {
    const response = await callConvertApi(
      userInput.from,
      userInput.to,
      userInput.amount,
    );
    setData(response);
    console.log(response);
  }

  useEffect(() => {
    if(!userInput.amount || Number(userInput.amount) <= 0) return;
    handleConvert();
  }, [userInput.from, userInput.to, userInput.amount]);

  useEffect(() => {
    const fetchCurrencies = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/currencies");
        const data = await res.json();
        setCurrencies(data);
      } catch (err) {
        console.error(err);
      }
    };
    fetchCurrencies();
  }, []);

  return (
    <div className="flex min-h-screen items-center justify-center bg-zinc-50 font-sans dark:bg-black">
      <main className="flex min-h-screen w-full max-w-3xl flex-col items-center justify-between py-32 px-16 bg-white dark:bg-black sm:items-start">
        <div className="flex flex-row gap-5 w-full justify-between">
          <Select
            value={userInput.from}
            onValueChange={(value) =>
              setUserInput({ ...userInput, from: value })
            }
          >
            <SelectTrigger className="w">
              <SelectValue placeholder="Select a currency" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>Valuta</SelectLabel>
                {curriencies.map((currency) => (
                  <SelectItem
                    key={currency}
                    value={currency}
                    onClick={() =>
                      setUserInput({ ...userInput, from: currency })
                    }
                  >
                    {currency}
                  </SelectItem>
                ))}
              </SelectGroup>
            </SelectContent>
          </Select>
          <Select
            value={userInput.to}
            onValueChange={(value) => setUserInput({ ...userInput, to: value })}
          >
            <SelectTrigger className="w-[180px]">
              <SelectValue placeholder="Select a currency" />
            </SelectTrigger>
            <SelectContent>
              <SelectGroup>
                <SelectLabel>Valuta</SelectLabel>
                {curriencies.map((currency) => (
                  <SelectItem
                    key={currency}
                    value={currency}
                    onClick={() => setUserInput({ ...userInput, to: currency })}
                  >
                    {currency}
                  </SelectItem>
                ))}
              </SelectGroup>
            </SelectContent>
          </Select>
        </div>
        <Input
          type="number"
          placeholder="Amount"
          className="w-full"
          value={userInput.amount}
          onChange={(e) =>
            setUserInput({ ...userInput, amount: e.target.value })
          }
        />

        <button
          onClick={handleConvert}
          className="rounded bg-blue-500 px-4 py-2 text-white hover:bg-blue-600"
        >
          Convert
        </button>
        {data && (
          <div className="flex flex-col justify-center content-center  gap-2 mt-5">
            <h1>From:{userInput.from}</h1>
            <h1>To: {userInput.to}</h1>
            <h1>
              Amount: {data.amount} {userInput.from}
            </h1>
            <h1>
              Rate: {data.rate} {userInput.to}
            </h1>
            <h1>
              Result: {data.result} {userInput.to}
            </h1>
          </div>
        )}
      </main>
    </div>
  );
}
