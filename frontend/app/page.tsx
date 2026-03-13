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

  const data = await res.json();
  if (!res.ok) {
    throw new Error(data.error || "Failed to convert currency");
  }
  return data as ConvertResponse;
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
    try {
      setError("");
    const response = await callConvertApi(
      userInput.from,
      userInput.to,
      userInput.amount,
    );
    setData(response);
    console.log(response);
    } catch (err) {
      setError((err as Error).message);
    } 
  }

  useEffect(() => {
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
      <main className="flex gap-14 w-full max-w-3xl flex-col items-center justify-between py-32 px-16 bg-white dark:bg-black">
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
        {data && (
          <div className="flex w-full border-0 rounded-lg flex-col gap-2 p-5 bg-zinc-100 dark:bg-zinc-800 items-center text-4xl font-extrabold ">
            <h1>
              {data.amount} {userInput.from} -- {data.result} {userInput.to}
            </h1>
            <h1>
              Rate: {data.rate} {userInput.to}
            </h1>
          </div>
        )}

        <h1>{error}</h1>
        <Input
          type="number"
          className="self-center text-xl"
          value={userInput.amount}
          onChange={(e) =>
            setUserInput({ ...userInput, amount: e.target.value })
          }
        />
      </main>
    </div>
  );
}
